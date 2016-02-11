# ToDoz
## android-todo-app
### Pre-work submission for CoderSchool.vn Android class

This todo app allows user to manage multiple lists, each with multiple tasks. Tasks can be edited, deleted, and completed.

Time Spent: 7 hours

Completed User Stories:
- (required) Allow users to add tasks
- (required) Allow users to delete tasks
- (required) Allow users to edit tasks
- (optional) Allow users to group tasks into lists
- (optional) Allow users to mark/toggle tasks as completed
- (optional) Allow users to see task completion progress for each list

1. Add a list, like "Groceries".
    - You can have multiple lists
    - Lists can be deleted by clicking and holding
    - If a list is deleted, all the tasks will also be deleted
    - List names cannot be edited
2. You will see "0/0", this means you have completed 0 of 0 tasks. Lets add some tasks.
3. Click on the list.
4. Add some tasks to your list.
5. Click on a task to mark it as completed.
6. Click and hold a task to bring up the detail activity.
    - Here you can change the task name and 'Save' it.
    - You can also delete the task
7. Click the back arrow to get back to the List screen. Here you can see all your lists and track your task progress.

Thing I want to improve:
1. Store lists and tasks in a database instead of in text files.
    - This will speed up the app. Right now every time a task is added or edited, all the task objects are serialized into JSON strings and written to file. Then the file is parsed into JSON and each line is initialized into an array of Java objects. This is slow.

2. Allow for users to drag and drop to reorder Lists and Tasks.
3. Allow users to add a photo with each task, e.g. image of particular brand of food for a grocery list. Or a picture to verify that something has been completed.
4. Allow users to share and sync lists with others.


