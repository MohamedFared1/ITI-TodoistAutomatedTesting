package Requests;

public class Environment {



    private static String projectId;
    private static String sectionId;
    private static String taskId;
    private static String commentId;
    private static String labelId;

    public static String getProjectId() {
        return projectId;
    }

    public static void setProjectId(String projectId) {
        Environment.projectId = projectId;
    }

    public static String getSectionId() {
        return sectionId;
    }

    public static void setSectionId(String sectionId) {
        Environment.sectionId = sectionId;
    }

    public static String getTaskId() {
        return taskId;
    }

    public static void setTaskId(String taskId) {
        Environment.taskId = taskId;
    }

    public static String getCommentId() {
        return commentId;
    }

    public static void setCommentId(String commentId) {
        Environment.commentId = commentId;
    }

    public static String getLabelId() {
        return labelId;
    }

    public static void setLabelId(String labelId) {
        Environment.labelId = labelId;
    }


}