package com.perich.todoz;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class List {

    public String name;  // List name


    List(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }


//      !!!!!!!!!!!!!!!
//    Static List Methods
//      !!!!!!!!!!!!!!!

    public static File getFileDir() {
        // This creates a global context, instead of passing around a Context
        // because Save/Read should be static methods and not need a passed Context
        return App.getContext().getFilesDir();
    }

    public static String taskFilename(String listName) {
        // This method defines the data filename for a list
        return listName + "data.txt";
    }

    public static String taskFilepath(String listName) {
        return List.getFileDir() + List.taskFilename(listName);
    }

    public static File listDataFile() {
        return new File(List.getFileDir(), "lists.txt");
    }

    public static File taskDataFile(String listName) {
        return new File(List.taskFilepath(listName));
    }

    public static ArrayList<List> all() {
        ArrayList<List> lists;
        try {
            ArrayList listStrings = new ArrayList<String>(FileUtils.readLines(List.listDataFile()));
            lists = List.stringsToLists(listStrings);
        } catch (IOException e) {
            lists = new ArrayList<List>();
        }
        return lists;
    }

    public static ArrayList<List> stringsToLists(ArrayList<String> listStrings) {
        ArrayList temp = new ArrayList<List>();
        for (String listString : listStrings) {
            temp.add(new List(listString));
        }
        return temp;
    }

    public static void saveAll(ArrayList<List> lists) {
        try{
            FileUtils.writeLines(List.listDataFile(), listsToStrings(lists));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<String> listsToStrings(ArrayList<List> lists) {
        ArrayList temp = new ArrayList<String>();
        for (List list : lists) {
            temp.add(list.getName());
        }
        return temp;
    }

    public static void deleteList(String listName) {
        // delete task file for list
        File taskDataFile = List.taskDataFile(listName);
        try {
            FileUtils.writeLines(taskDataFile, new ArrayList<String>());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Integer numTasksTotal(String listName) {
        ArrayList<Task> tasks = List.taskArray(listName);
        return tasks.size();
    }

    public static Integer numTasksCompleted(String listName) {
        ArrayList<Task> tasks = List.taskArray(listName);
        Integer completed = 0;
        for (Task task : tasks) {
            if (task.getStatus() == 1) {
                completed++;
            }
        }
        return completed;
    }

    public static ArrayList taskArray(String listName) {
        // This method defines the data filename for a list
        ArrayList<Task> tasks;
        File dataFile = List.taskDataFile(listName);
        try {
            ArrayList jsonTasks = new ArrayList<String>(FileUtils.readLines(dataFile));
            tasks = List.jsonToTasks(jsonTasks);
        } catch (IOException e) {
            tasks = new ArrayList<Task>();
        }
        return tasks;
    }

    private static ArrayList jsonToTasks(ArrayList<String> jsonTasks) {
        ArrayList temp = new ArrayList<Task>();
        for (String task : jsonTasks) {
            try {
                JSONObject jsonTask = new JSONObject(task);
                String name = jsonTask.getString("name");
                Integer status = jsonTask.getInt("status");
                Task tempTask = new Task(name, status);
                temp.add(tempTask);
            } catch (Exception e) {}
        }
        return temp;
    }

    public static void saveTasks(String listName, ArrayList<Task> tasks) {
        ArrayList<String> tasksAsStrings = List.tasksToJsonString(tasks);
        File dataFile = List.taskDataFile(listName);
        // Write to file
        try{
            FileUtils.writeLines(dataFile, tasksAsStrings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<String> tasksToJsonString(ArrayList<Task> tasks) {
        ArrayList temp = new ArrayList<String>();
        for (Task task : tasks) {
            temp.add(task.toJSON().toString());
        }
        return temp;
    }
}
