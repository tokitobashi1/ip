# FriendlyBotSakura – User Guide

Welcome to **FriendlyBotSakura**, your personal task manager chatbot!  
This guide covers the **GUI version** of FriendlyBotSakura.

---

##Getting Started

1. Launch the GUI application.  
2. Type your commands in the input box at the bottom.  
3. Click the **Send** button.  
4. FriendlyBotSakura will respond in the main display area.  

> 💡 **Tip:** Type `help` anytime to see the list of supported commands.

---

## Supported Commands

| Command | Description | Example |
|---------|-------------|---------|
| `list` | Show all tasks currently in your list | `list` |
| `todo [description]` | Add a **ToDo task** | `todo buy groceries` |
| `deadline [description] /by [yyyy-mm-dd]` | Add a **Deadline task** with a due date | `deadline submit report /by 2025-09-20` |
| `event [description] /from [start] /to [end]` | Add an **Event task** with a start and end time | `event project meeting /from 2pm /to 4pm` |
| `mark [task number]` | Mark a task as **done** | `mark 1` |
| `unmark [task number]` | Mark a task as **not done** | `unmark 1` |
| `delete [task number]` | Delete a task | `delete 2` |
| `find [keyword]` | Search for tasks containing a keyword | `find project` |
| `help` | Show all available commands | `help` |
| `bye` | Exit the application | `bye` |

---

## Example Session


> help
Here are the commands you can use:
 list                     : List all tasks
 todo [description]       : Add a todo task
 deadline [desc] /by [date] : Add a deadline task
 event [desc] /from [start] /to [end] : Add an event
 mark [num]               : Mark a task as done
 unmark [num]             : Unmark a task
 delete [num]             : Delete a task
 find [keyword]           : Find tasks containing a keyword
 bye                      : Exit the app
 help                     : Show this help message

> todo buy milk
I have added this task:
  [T][ ] buy milk
You now have 1 task in the list.

> list
Here are the tasks in your list:
 1. [T][ ] buy milk

> mark 1
Nice! I've marked this task as done:
  [T][X] buy milk

> bye
Bye, I wish you a pleasant day!