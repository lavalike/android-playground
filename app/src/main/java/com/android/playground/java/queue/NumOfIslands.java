package com.android.playground.java.queue;

/**
 * 岛屿数量
 * 给定一个由 '1'（陆地）和 '0'（水）组成的的二维网格，计算岛屿的数量。
 * 一个岛被水包围，并且它是通过水平方向或垂直方向上相邻的陆地连接而成的。你可以假设网格的四个边均被水包围。
 * <p>
 * 示例 1:
 * <p>
 * 输入:
 * 11110
 * 11010
 * 11000
 * 00000
 * <p>
 * 输出: 1
 * 示例 2:
 * <p>
 * 输入:
 * 11000
 * 11000
 * 00100
 * 00011
 * <p>
 * 输出: 3
 * Created by laval on 2020/2/23.
 */
public class NumOfIslands {
    public static void main(String[] args) {
        char[][] grid = new char[][]{
                {'1', '1', '0', '0', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '1', '0', '0'},
                {'0', '0', '0', '1', '1'}
        };

        grid = new char[][]{
                {'1', '0', '1'},
                {'1', '1', '0', '1'},
                {'1', '1'},
                {'0', '0', '0', '1', '1'}
        };
        NumOfIslands instance = new NumOfIslands();
        System.out.println("number of islands is : " + instance.numIslands(grid));
    }

    private int numIslands(char[][] grid) {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '1') {
                    //遍历判断[i,j]相邻坐标是否为'1'，如果为'1'修改为'0'，防止干扰判断
                    dfs(grid, i, j);
                    count++;
                }
            }
        }
        return count;
    }

    private void dfs(char[][] grid, int i, int j) {
        if (i >= 0 && j >= 0 && i < grid.length && j < grid[i].length) {
            if (grid[i][j] == '1') {
                grid[i][j] = '0';
                dfs(grid, i - 1, j);
                dfs(grid, i, j - 1);
                dfs(grid, i + 1, j);
                dfs(grid, i, j + 1);
            }
        }
    }
}
