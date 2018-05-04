package com.wangzhen.processors;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.wangzhen.annotations.BindView;
import com.wangzhen.processors.entity.VariableEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

@AutoService(Processor.class)
@SupportedAnnotationTypes("com.wangzhen.annotations.BindView")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class BindViewProcessor extends AbstractProcessor {

    private Filer filer;
    //存储相同class下所有被注解信息
    private Map<String, List<VariableEntity>> classMap = new HashMap<>();
    //存储class对应的TypeElement
    private Map<String, TypeElement> classType = new HashMap<>();
    private Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
        elementUtils = processingEnvironment.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        collection(roundEnvironment);
        writeFile();
        return true;
    }

    private void collection(RoundEnvironment roundEnvironment) {
        classMap.clear();
        classType.clear();
        for (Element element : roundEnvironment.getElementsAnnotatedWith(BindView.class)) {
            int viewId = element.getAnnotation(BindView.class).value();
            VariableElement variableElement = (VariableElement) element;
            TypeElement typeElement = (TypeElement) element.getEnclosingElement();
            String classFullName = typeElement.getQualifiedName().toString();
            List<VariableEntity> variableEntities = classMap.get(classFullName);
            if (variableEntities == null) {
                variableEntities = new ArrayList<>();
                classMap.put(classFullName, variableEntities);
                classType.put(classFullName, typeElement);
            }
            VariableEntity variableEntity = new VariableEntity();
            variableEntity.setId(viewId);
            variableEntity.setElement(variableElement);
            variableEntities.add(variableEntity);
        }
    }

    private void writeFile() {
        for (String classFullName : classMap.keySet()) {
            TypeElement typeElement = classType.get(classFullName);

            MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(ParameterSpec.builder(TypeName.get(typeElement.asType()), "activity").build());

            List<VariableEntity> variableEntities = classMap.get(classFullName);
            for (VariableEntity entity : variableEntities) {
                int id = entity.getId();
                VariableElement element = entity.getElement();
                // 变量名称(比如：TextView tv 的 tv)
                String variableName = element.getSimpleName().toString();
                // 变量类型的完整类路径（比如：android.widget.TextView）
                String variableType = element.asType().toString();
                // 在构造方法中增加赋值语句，例如：activity.tv = (android.widget.TextView)activity.findViewById(215334);
                constructorBuilder.addStatement("activity.$L=($L)activity.findViewById($L)", variableName, variableType, id);
            }

            TypeSpec typeSpec = TypeSpec.classBuilder(typeElement.getSimpleName() + "$$ViewBinder")
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(constructorBuilder.build())
                    .build();

            String packageName = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
            JavaFile javaFile = JavaFile.builder(packageName, typeSpec)
                    .addFileComment("auto-generated file, do not modify.")
                    .build();

            try {
                javaFile.writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
