# Pre-work - ToDoz
## android-todo-app

**ToDoz** allows user to manage multiple lists, each with multiple tasks. Tasks can be edited, deleted, and completed.

Submitted by: Peter Richards

Time spent: About 8 hours spent in total

## User Stories

The following **required** functionality is completed:

* [X] User can **successfully add and remove items** from the todo list
* [X] User can **hold a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [X] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [ ] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [X] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [ ] Add support for completion due dates for todo items (and display within listview item)
* [ ] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [ ] Add support for selecting the priority of each todo item (and display in listview item)
* [X] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [X] Allow users to group tasks into lists
* [X] Allow users to mark/toggle tasks as completed
* [X] Allow users to see task completion progress for each list

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

<img src='http://imgur.com/57qeFvN' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

### Challenges:

Without the use of a database, I found the data cumbersome to deal with. Everything had to passed around. Also the saving is messy since each save requires serializing Java objects into JSON strings, and copying them to a data file, then parsing the JSON back into Java objects and rendering them on the page. Since the amount of data is small, this won't affect app performance too much, but it makes the code more confusing that it needs to be, and it makes adding and changing functionality more complex. With a DB objects can easily be created/updated/read/deleted individually.

### Things I want to improve:

1. Store lists and tasks in a database instead of in text files.
    - This will speed up the app, both in terms of development and performance.
    * I plan to add this before the start of the course.

2. Allow for users to drag and drop to reorder Lists and Tasks.
3. Allow users to add a photo with each task, e.g. image of particular brand of food for a grocery list. Or a picture to verify that something has been completed.
4. Allow users to share and sync lists with others.

## License

    Copyright 2016 Peter Richards

    MIT License