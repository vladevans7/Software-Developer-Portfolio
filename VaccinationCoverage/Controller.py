
# The standard Python interface to the Tcl/Tk GUI toolkit
from tkinter import *
# The pandastable library provides a table widget for Tkinter with plotting and data manipulation functionality
from pandastable import Table
# The standard Python interface to the Tcl/Tk GUI toolkit
from tkinter import messagebox

from Model import FileCSVData, DataBaseTable, Model
# The matplotlib is a plotting library for the Python programming language
import matplotlib.pyplot as plt


# Class for editing and manipulating table data
class Controller:
    def __init__(self, win, nr):
        #   Our constructor. table - Instance of the class for file loading and unloading tables

        self.win = win              # Our application window
        self.file_name = ''         # Variable for out file name
        self.df = None              # Our data frame
        self.f2 = None              # Our second frame
        self.n_rows = nr            # Number of rows
        self.model = Model("database.db")
        self.table = None          # Our main Table
        self.table2 = None          # Our extra table
        self.table_array = [[]]     # Array for storing cells of our table
        self.table_title = []       # Array for storing our column names

    def init_table_array(self):
        #   Here we create arrays with table values. table - Instance of the class for file loading and unloading tables

        self.table_array = self.df.values
        self.table_title = self.df.columns

    def show_rows_range(self, start, quantity):
        # This method helps us to display a range of rows. Start the starting index. Quantity - number of rows
        if self.df is not None:
            if self.f2 is not None:
                self.table.master.destroy()
                self.table.destroy()
                self.clear_table2()
                self.f2 = Frame(self.win)
                self.f2.pack(fill=BOTH, expand=1)
                self.table2 = Table(self.f2, dataframe=self.df[start:start + quantity], read_only=False)
                self.table2.show()

    def clear_table2(self):
        #   In this methid we clear our table
        if self.table2 is not None:
            self.table2.master.destroy()
            self.table2.destroy()

    def create_new_record(self):
        #   In this method we create a new record
        if self.df is not None:
            if self.f2 is not None:
                self.table.addRow()

    def load_new_table(self, nr, is_db):
        #   Function for loading a new table from CSV file
        self.clear_table2()
        self.model.file = True
        self.model.is_db = is_db
        self.n_rows = nr
        self.model.get_table(self)
        self.init_table_array()

    def save_new_table(self, is_db):
        #   Function for saving our table
        if self.table is None:
            return
        self.model.is_db = is_db
        self.df = self.table.model.df
        self.model.save_table(self)

    def insert_one_row(self):
        #   Function for inserting a row
        self.create_new_record()
        self.init_table_array()

    def delete_one_row(self):
        #   Function for deleting a record
        self.delete_one_record(self.table.currentrow, True)

    def delete_one_record(self, n_row, ask=False):
        #   In this method we delete one row by index.
        #   n_row - row number
        #   ask - parameter to ask before we delete our row
        if self.df is not None:
            if self.f2 is not None:
                n = False
                if ask:
                    n = messagebox.askyesno("Delete", "Delete this row?", parent=self.table.parentframe)
                if n or not ask:
                    if self.table is not None:
                        self.table.storeCurrent()
                        self.table.model.deleteRows([n_row])
                        self.table.setSelectedRow(n_row - 1)
                        self.table.clearSelected()
                        self.table.update_rowcolors()
                        self.table.redraw()
                    self.df.drop(n_row, inplace=True)
                    self.init_table_array()

    def bar_chart(self, column):
        # We need this function for construction our bar chart (Assignment 4)
        # column - is the column of the table, used to plot bar chart
        if self.df is not None:
            self.df.pivot(columns='prename', values=column).plot(kind='bar', figsize=(10, 7))
            plt.xlabel('Report number')
            plt.ylabel(column)
            plt.title('Vaccination Canada')
            plt.legend(loc='upper right')
            plt.show()
