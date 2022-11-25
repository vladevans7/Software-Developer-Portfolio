
#   The pandastable library provides a table widget for Tkinter with plotting and data manipulation functionality.
import pandas as pd
from unittest import TestCase
from Controller import Controller


class TestController(TestCase):
    # one row deletion test
    def test_delete_one_record(self):
        print("Vlad Evans Unit-Test")
        # here we create an empty application instance
        app = None
        logic = Controller(app, 10)
        # Frame must be not null to work properly
        logic.f2 = 1
        #   Here we create a test table
        logic.df = pd.DataFrame({'numbers': [1, 2, 3], 'colors': ['red', 'white', 'blue']})

        logic.init_table_array()
        # Here we check the number of rows
        self.assertEqual(len(logic.df), 3)
        self.assertEqual(len(logic.table_array), 3)
        # Then we delete one row
        logic.delete_one_record(2)
        # After deletion we check the number of rows again
        self.assertEqual(len(logic.df), 2)
        self.assertEqual(len(logic.table_array), 2)