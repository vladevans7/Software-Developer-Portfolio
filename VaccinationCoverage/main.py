
# The standard Python interface to the Tcl/Tk GUI toolkit
from tkinter import *

from Controller import Controller
from Model import Model
from View import View

# Start the program and all our classes

appWin = Tk()                   # We are creating a new window
appWin.title('Vlad Evans Vaccination Coverage')  # Name of the window

table = Model(appWin)           # We need this for loading and unloading tables
n_rows = 100                    # Default number of rows to load
logic = Controller(appWin, n_rows)       # We create an instance of logic
view = View(logic)      # We create our GUI
appWin.geometry('800x600')
appWin.mainloop()               # We start the main loop of our program
