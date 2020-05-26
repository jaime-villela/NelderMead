import matplotlib.pyplot as plt
from Triangle import SimplexTriangle2Dim

class DownhillSimplex2Dim():
    def __init__(self, x1, y1, x2, y2, x3, y3, func):
        self.triangle = SimplexTriangle2Dim(x1, y1, x2, y2, x3, y3, func)
        self.num_iterations = 0
        self.font_size = 8

    def find_local_min(self):
        continue_loop = True

        while continue_loop:
            continue_loop = self.cycle_through_algorithm()

        plt.gca().autoscale()
        plt.show()
        return self.triangle.get_best_x_y()

    def cycle_through_algorithm(self):
        cycle_again = True
        self.update_iterator_and_vertices()

        if self.triangle.did_vertices_converge():
            cycle_again = False
        else:
            self.transform_and_graph()

        return cycle_again

    def update_iterator_and_vertices(self):
        self.update_iteration_count()
        self.determine_best_good_worst_points()

    def transform_and_graph(self):
        self.transform_triangle()
        self.update_graph()

    def update_iteration_count(self):
        self.num_iterations += 1
        print "Iteration: %d" % self.num_iterations

    def update_graph(self):
        self.add_triangle_to_graph()
        self.label_triangle()

    def determine_best_good_worst_points(self):
        self.triangle.eval_func_at_vertices()
        self.triangle.sort_vertices()
        print(self.triangle)

    def transform_triangle(self):
        self.triangle.calc_reflection_point()
        self.triangle.eval_func_at_reflection()

        if self.triangle.is_reflection_lt_good():
            self.triangle.reflect_or_expand()
        else:
            self.triangle.contract_or_shrink()

    def time_to_reduce_font(self):
        iterations_before_reducing = 8
        return self.num_iterations % iterations_before_reducing == 0

    def adjust_font_size(self):
        if self.time_to_reduce_font():
            self.font_size = self.font_size - 2

    def label_triangle(self):
        text = self.num_iterations
        text_x_y = self.triangle.get_good_to_worst_midpoint()
        self.adjust_font_size()
        plt.annotate(text, text_x_y, fontsize=self.font_size)

    def adjust_opacity(self):
        opacity_increase_rate = 0.02
        return self.num_iterations * opacity_increase_rate

    def add_triangle_to_graph(self):
        pts = self.triangle.get_vertices()
        opacity = self.adjust_opacity()
        polygon = plt.Polygon(pts, alpha=opacity, edgecolor='k')
        plt.gca().add_patch(polygon)
