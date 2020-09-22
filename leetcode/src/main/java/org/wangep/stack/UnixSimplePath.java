package org.wangep.stack;

/***
 * created by wange on 2020/9/17 10:34
 */
public class UnixSimplePath {
    public static void main(String[] args) {
        System.out.println(simplifyPath("/../"));
    }

    public static String simplifyPath(String path) {
        if (path == null || "".equalsIgnoreCase(path)) {
            return "";
        }
        String[] dirs = path.split("/");
        int top = -1;
        String[] stack = new String[dirs.length];

        for (String dir : dirs) {
            if (".".equals(dir) || "".equals(dir)) {

            } else if ("..".equals(dir)) {
                if (top > -1) {
                    stack[top--] = null;
                }
            } else {
                stack[++top] = dir;
            }
        }

        if (top > -1) {
            String newPath = "/";
            for (String s : stack) {
                if (s != null && !"".equals(s)) {
                    newPath = newPath + s + "/";
                }
            }
            return newPath.substring(0, newPath.length() - 1);
        }
        return "/";
    }


}
