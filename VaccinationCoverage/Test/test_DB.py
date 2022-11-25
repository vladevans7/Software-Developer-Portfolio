
from unittest import TestCase
#   The pandastable library provides a table widget for Tkinter with plotting and data manipulation functionality.
import pandas as pd
from Controller import Controller


# In this test we verify that the table is written to database and can be read from database without mistakes.
class TestDB(TestCase):
    def test_save_and_get_db(self):
        print("Vlad Evans Unit-Test")
        # Verification that the table is written to database and can be read from database without mistakes.
        # Here we create an instance of an empty application.
        app = None
        # Here we create the first instance of a controller
        logic1 = Controller(app, 1000)
        # Here we create a DataFrame as a table of 4 rows
        logic1.df = pd.DataFrame({'numbers': [1, 2, 3, 4], 'colors': ['red', 'white', 'blue', 'yellow']})
        # Here we switch the model of the controller 1 to the database mode
        logic1.model.is_db = True
        # Here we save the table to the database
        logic1.model.save_table(logic1)
        # Here we create the second instance of a controller
        logic2 = Controller(app, 1000)
        # Here we create a DataFrame as a table of 2 rows
        logic2.df = pd.DataFrame({'numbers': [1, 2], 'colors': ['red', 'white']})
        # Here we switch the model of the controller 2 to database mode
        logic2.model.is_db = True
        # Here we read the table from the database
        logic2.model.get_table(logic2)
        # Here we check the number of rows
        self.assertEqual(len(logic2.df), len(logic1.df))
        print(logic2.df.head())
