import unittest
from NelderMead import DownhillSimplex2Dim

def polynomial_1(x, y):
    return (x * x) - (4 * x) + (y * y) - y - (x * y)

class NelderMeadTestCase(unittest.TestCase):
    def setUp(self):
        self.dh_simplex = DownhillSimplex2Dim(0, 0, 1.2, 0, 0, 0.8, polynomial_1)

    def test_creating_object(self):
        self.assertIsInstance(self.dh_simplex, DownhillSimplex2Dim)

    def test_find_local_min(self):
        local_min = self.dh_simplex.find_local_min()
        self.assertAlmostEqual(3.0, local_min[0], places=1)
        self.assertAlmostEqual(2.0, local_min[1], places=1)

    def tearDown(self):
        del self.dh_simplex


if __name__ == '__main__':
    unittest.main()
