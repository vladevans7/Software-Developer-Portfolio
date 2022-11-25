
#   Pandas is a Python library. Pandas is used to analyze data.
import pandas as pd
#   The standard Python interface to the Tcl/Tk GUI toolkit
from tkinter import *
from tkinter import filedialog
from tkinter import messagebox as msg
#   The pandastable library provides a table widget for Tkinter with plotting and data manipulation functionality.
from pandastable import Table

import sqlite3
from sqlalchemy import create_engine
from DataManipulation import DataManipulation


# In this class we manage file input and output
class Model(DataManipulation):
    def __init__(self, file_name):
        # Our constructor. file_name - name of the file.

        super().__init__(file_name)
        self.is_db = False                          # If we have true here, that means that we are in a database mode
        self.file_csv = FileCSVData(self.file_name) # This is the variable for interacting with the CSV file
        self.db = DataBaseTable(self.file_name)     # THis is the variable for interacting with the database

    def save_table(self, logic):
        # Method for saving the table. logic - this is the instance of the controller class
        if self.is_db:
            self.db.save_table(logic)
        else:
            self.file_csv.save_table(logic)

    def get_table(self, logic):
        # This method is for table retrieval. logic - this is the instance of the controller class
        if self.is_db:
            self.db.get_table(logic)
        else:
            self.file_csv.get_table(logic)


# In this class we manage CSV file input and output
class FileCSVData(DataManipulation):
    def __init__(self, file_name):
        # Constructor. file_name - name of the file.
        super().__init__(file_name)

    def save_table(self, logic):
        # We need this method for saving modified table to a CSV file.
        # logic - this is the instance of the controller class.
        # Attempt of saving to file.
        try:
            # Here we choose the name of the file and the location.
            self.file_name = filedialog.asksaveasfilename(initialdir='/Desktop',
                                                          title='Enter a new CSV filename',
                                                          filetypes=(('csv file', '*.csv'),
                                                                     ('csv file', '*.csv')))
            # file_name - is the name of the file, where we export the table.
            logic.table.doExport(self.file_name)
        except IOError as e:
            msg.showerror('Error saving file', e)

    def get_table(self, logic):
        # We need this method to get the table from CSV file.
        # logic - this is the instance of the controller class.
        if logic.n_rows > 0:
            # Attempt of loading the table from file.
            try:
                # In this dialog we select the name of the file.
                self.file_name = filedialog.askopenfilename(initialdir='/Desktop',
                                                            title='Select a CSV file',
                                                            filetypes=(('csv file', '*.csv'),
                                                                       ('csv file', '*.csv')))
                logic.df = None
                # Here we read the csv file and get the table.
                # elf.file_name - name of the file.
                # DATAFrame - we put the table here.
                logic.df = pd.read_csv(self.file_name, nrows=logic.n_rows)
                logic.n_rows = len(logic.df)
                if len(logic.df) == 0:
                    msg.showinfo('No records', 'No records')

                # If the table is already loaded - we delete it.
                if logic.f2 is not None:
                    logic.table.master.destroy()
                    logic.table.destroy()

                # Here we display the DF in 'Table' object
                logic.f2 = Frame(logic.win)
                logic.f2.pack(fill=BOTH, expand=1)
                logic.table = Table(logic.f2, dataframe=logic.df, read_only=False)

                # Here we display table in a window
                logic.table.show()
            # Here we catch an exception
            except FileNotFoundError as e:  #
                print(e)
                msg.showerror('Error in opening file', e)


# Here is our class for managing input and output.
class DataBaseTable(DataManipulation):
    def __init__(self, file_name):
        # Here is our constructor. file_name - here is our name of the file.
        super().__init__(file_name)

    def save_table(self, logic):
        # Here is our method for saving the table to database.
        # logic - this is the instance of the controller class.
        if logic.df is not None:
            if 'index' in logic.df.columns:
                del logic.df['index']
            engine = create_engine('sqlite:///'+self.file_name, echo=True)
            sqlite_connection = engine.connect()
            logic.df.to_sql("table_db", sqlite_connection, if_exists='replace')
            sqlite_connection.close()

        # Here we try to save to a file

    def get_table(self, logic):
        # Here is our method for getting the table from database.
        # logic - this is the instance of the controller class.
        if logic.n_rows > 0:
            # Here we put query results into a DataFrame
            con = sqlite3.connect(self.file_name)
            logic.df = pd.read_sql_query('SELECT * from table_db', con)
            con.close()

            logic.n_rows = logic.df.count()
            if len(logic.df) == 0:
                msg.showinfo('No records', 'No records')
            else:
                pass

            # If the table is loaded - we delete it.
            if logic.f2 is not None:
                logic.table.master.destroy()
                logic.table.destroy()

            # Here we display the DF
            logic.f2 = Frame(logic.win)
            logic.f2.pack(fill=BOTH, expand=1)
            logic.table = Table(logic.f2, dataframe=logic.df, read_only=False)

            # Here we display a table in a window
            logic.table.show()
