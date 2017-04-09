// ICalculator.aidl
package com.android.exercise;

// Declare any non-default types here with import statements
import com.android.exercise.domain.aidl.Person;

interface IMyAidl {
    //计算两个数字的和
    int addNumber(int num1,int num2);

    List<Person> addPerson(in Person person);
}
