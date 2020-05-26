import unittest
from Point import Point

def sum_square_func(x, y):
    return (x * x) + (y * y)

def polynomial_1(x, y):
    return (x * x) - (4 * x) + (y * y) - y - (x * y)

class PointTestCase(unittest.TestCase):
    def test_object_creation(self):
        p = Point(1.2, 0.0)
        self.assertIsInstance(p, Point)
        self.assertEqual(1.2, p.x)
        self.assertEqual(0.0, p.y)

    def test_addition(self):
        a = Point(1, 2)
        b = Point(3, 4)
        c = a + b
        self.assertEqual(4, c.x)
        self.assertEqual(6, c.y)

    def test_subtraction(self):
        a = Point(1, 2)
        b = Point(3, 4)
        c = a - b
        self.assertEqual(-2, c.x)
        self.assertEqual(-2, c.y)

    def test_multiplication(self):
        a = Point(1, 2)
        c = 2 * a
        b = Point(3.0, 4.0)
        d = 2 * b
        self.assertEqual(2.0, c.x)
        self.assertEqual(4.0, c.y)
        self.assertEqual(6.0, d.x)
        self.assertEqual(8.0, d.y)

    def test_division(self):
        a = Point(1, 2)
        b = a / 2.0
        self.assertEqual(0.5, b.x)
        self.assertEqual(1, b.y)

    def test_eval_at_func(self):
        a = Point(3, 4)
        a.evaluate_function(sum_square_func)
        self.assertEqual(25, a.z)

    def test_sorted_points(self):
        v1 = Point(0.0, 0.0)
        v2 = Point(1.2, 0.0)
        v3 = Point(0.0, 0.8)
        v1.evaluate_function(polynomial_1)
        v2.evaluate_function(polynomial_1)
        v3.evaluate_function(polynomial_1)
        all_vertices = (v1, v2, v3)
        sorted_vertices = sorted(all_vertices, key=lambda o:o.z)
        best_point = sorted_vertices[0]
        good_point = sorted_vertices[1]
        worst_point = sorted_vertices[2]
        self.assertAlmostEqual(-3.36, best_point.z, places=3)
        self.assertAlmostEqual(-0.16, good_point.z, places=3)
        self.assertAlmostEqual(0.0, worst_point.z, places=3)

    def test_format_print(self):
        p = Point(1.2, 0)
        p.evaluate_function(polynomial_1)
        self.assertEquals("x:1.2, y:0.0, z:-3.36", "{}".format(p))
        self.assertEquals("x:1.2, y:0.0, z:-3.36", repr(p))

    def test_assignment(self):
        a = Point(1, 2)
        b = a
        c = a.copy()
        self.assertEqual(a.x, b.x)
        self.assertEqual(a.x, c.x)

if __name__ == '__main__':
    unittest.main()
