import unittest
from Triangle import SimplexTriangle2Dim

def polynomial_1(x, y):
    return (x * x) - (4 * x) + (y * y) - y - (x * y)

class SimplexTriangleTestCase(unittest.TestCase):
    def setUp(self):
        self.t = SimplexTriangle2Dim(0, 0, 1.2, 0.0, 0.0, 0.8, polynomial_1)
        self.t.eval_func_at_vertices()

    def tearDown(self):
        del self.t

    def test_object_creation(self):
        self.assertIsInstance(self.t, SimplexTriangle2Dim)

    def test_evaluate_function(self):
        self.assertEquals("x:0.0, y:0.0, z:0.00", "{}".format(self.t.best))
        self.assertEquals("x:1.2, y:0.0, z:-3.36", "{}".format(self.t.good))
        self.assertEquals("x:0.0, y:0.8, z:-0.16", "{}".format(self.t.worst))

    def test_vertices_sort(self):
        self.t.sort_vertices()
        self.assertEquals("x:1.2, y:0.0, z:-3.36", "{}".format(self.t.best))
        self.assertEquals("x:0.0, y:0.8, z:-0.16", "{}".format(self.t.good))
        self.assertEquals("x:0.0, y:0.0, z:0.00", "{}".format(self.t.worst))

    def test_calc_midpoint(self):
        self.t.sort_vertices()
        self.t.calc_mid_best_to_good()
        self.assertEquals("x:0.6, y:0.4, z:0.00", "{}".format(self.t.mid_b_to_g))

    def test_calc_reflection(self):
        self.t.sort_vertices()
        self.t.calc_reflection_point()
        self.assertEquals("x:1.2, y:0.8, z:0.00", "{}".format(self.t.reflection))

    def test_calc_expansion(self):
        self.t.sort_vertices()
        self.t.calc_expansion_point()
        self.assertEquals("x:1.8, y:1.2, z:0.00", "{}".format(self.t.expansion))

    def test_print(self):
        print(self.t)


if __name__ == '__main__':
    unittest.main()
