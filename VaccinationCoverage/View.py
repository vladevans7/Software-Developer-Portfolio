
# The standard Python interface to the Tcl/Tk GUI toolkit
from tkinter import *
# The pandastable library provides a table widget for Tkinter with plotting and data manipulation functionality.
from pandastable import Table
import sqlite3
# One of the UI widgets in the Tkinter library (a combination of an Entry widget and a Listbox widget)
from tkinter.ttk import Combobox


# This class is responsible for the layout of "widgets" in our window
class View:
    def __init__(self, logic):
        # Our constructor.
        # logic - is the instance of the Controller class
        # nr - is the initial setting of how many rows to load
        self.frame = Frame(logic.table)
        self.frame.pack()

        # Here we create the widgets

        # Here is our window title
        self.text_label = Label(self.frame, text='Excel file viewer', font=('Calibri Bold Italic', 15), fg='#000000')

        # Here is the field for entering the number of rows we are going to load
        var = IntVar()
        self.n_rows = Entry(self.frame, width=3, font=('Calibri Bold Italic', 14), textvariable=var)
        # Our initial value
        var.set(logic.n_rows)

        # Excel file upload button
        self.load_file_button = Button(self.frame, text='Upload excel file', font=('Calibri Bold Italic', 14), bg='#FFA7A7', fg='#000000', command=lambda: logic.load_new_table(int(self.n_rows.get()), False))

        # Load from DB button
        self.load_from_db = Button(self.frame, text='Load table from DB', font=('Calibri Bold Italic', 14), bg='#FFA7A7', fg='#000000', command=lambda: logic.load_new_table(int(self.n_rows.get()), True))

        self.rows = Label(self.frame, text='Number of rows', font=('Calibri Bold Italic', 14), fg='#000000')

        # Excel file save button
        self.save_file_button = Button(self.frame, text='Save the table to excel file', font=('Calibri Bold Italic', 14), bg='#FFA7A7', fg='#000000', command=lambda: logic.save_new_table(False))

        # Save to DB button
        self.save_to_db = Button(self.frame, text='Save table to DB', font=('Calibri Bold Italic', 14), bg='#FFA7A7', fg='#000000', command=lambda: logic.save_new_table(True))

        # Stop program button
        self.exit_button = Button(self.frame, text='Exit program', font=('Calibri Bold Italic', 14), bg='#ff0000', fg='#000000', width=10, command=logic.win.destroy)

        var1 = IntVar()
        # In this field we enter the number of rows we want to display
        self.rows_quantity = Entry(self.frame, width=4, font=('Calibri Bold Italic', 13), textvariable=var1)
        var1.set(1)

        self.rows2 = Label(self.frame, text='rows from              position', font=('Calibri Bold Italic', 14), fg='#000000')

        var2 = IntVar()
        # In this field we enter the starting number of rows
        self.rows_start = Entry(self.frame, width=4, font=('Calibri Bold Italic', 13), textvariable=var2)
        var2.set(10)

        def combo_values_input():
            # We need this method for generating a list of column names
            # We return the list of column names

            conn = sqlite3.connect('database.db')
            cur = conn.cursor()
            query = cur.execute("PRAGMA table_info('table_db')")
            data = []
            for row in cur.fetchall():
                if row[2] == "BIGINT" or row[2] == "FLOAT":
                    data.append(row[1])
            cur.close()
            conn.close()
            return data

        # Here we form the Vertical Bar Chart
        self.labelframe = LabelFrame(self.frame, text="Vertical Bar Chart", width=300, height=80)

        # Our button for the Vertical Bar Chart
        self.vertical_bar_chart = Button(self.frame, text='Build Chart', font=('Calibri Bold Italic', 14), bg='#FFA7A7', fg='#000000', command=lambda: logic.bar_chart(self.drop_down_list.get()))

        # Our drop-down list
        self.drop_down_list = Combobox(self.frame, width=20, height=20)
        self.drop_down_list['values'] = combo_values_input()
        self.drop_down_list.current(2)
        self.drop_down_list.pack()

        # This is the button to show a range of rows
        self.show_rows_range = Button(self.frame, text='Show', font=('Calibri Bold Italic', 14), bg='#FFA7A7', fg='#000000', command=lambda: logic.show_rows_range(int(self.rows_start.get()) - 1, int(self.rows_quantity.get())))

        # This is the button to create a new row
        self.create_row = Button(self.frame, text='Create new row in selected', font=('Calibri Bold Italic', 14), bg='#FFA7A7', fg='#000000', command=lambda: logic.insert_one_row())

        # This is the button to delete the row, which we selected
        self.delete_row = Button(self.frame, text='Delete row in selected', font=('Calibri Bold Italic', 14), bg='#FFA7A7', fg='#000000', command=lambda: logic.delete_one_row())

        # Here we have to=he coordinates of our widgets
        self.labelframe.place(x=280, y=180)
        self.drop_down_list.place(x=300, y=200)
        self.vertical_bar_chart.place(x=460, y=200)

        self.frame.pack(fill="both", expand=True)
        self.text_label.place(x=30, y=20)
        self.load_file_button.place(x=30, y=80)
        self.load_from_db.place(x=450, y=80)
        self.n_rows.place(x=220, y=90)
        self.rows.place(x=280, y=85)
        self.save_file_button.place(x=30, y=250)
        self.save_to_db.place(relx=0.87, y=80, anchor=NE)
        self.exit_button.place(relx=0.97, y=30, anchor=NE)
        self.show_rows_range.place(x=30, y=140)
        self.rows_quantity.place(x=110, y=150)
        self.rows2.place(x=160, y=145)
        self.rows_start.place(x=250, y=150)
        self.create_row.place(x=30, y=200)
        self.delete_row.place(x=700, y=200)

        # Here we display empty table, before it gets populated with data from the file
        logic.f2 = Frame(logic.win)
        logic.f2.pack(fill=BOTH, expand=1)
        logic.table = Table(logic.f2, dataframe=logic.df, read_only=False)
        logic.table.show()
