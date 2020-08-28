package seamcarving;

//import edu.princeton.cs.algs4.Picture;
import graphs.Edge;
import graphs.Graph;
import graphs.shortestpaths.DijkstraShortestPathFinder;
import graphs.shortestpaths.ShortestPath;
import graphs.shortestpaths.ShortestPathFinder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.List;

public class DijkstraSeamFinder implements SeamFinder {
    //private Picture picture;
    private static class MyVertex {
        private int x;
        private int y;
        public MyVertex(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public int getX() {
            return this.x;
        }
        public int getY() {
            return this.y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            MyVertex myVertex = (MyVertex) o;
            return x == myVertex.x &&
                y == myVertex.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
    public static class MyGraph implements Graph<MyVertex, Edge<MyVertex>> {
        private boolean horizontal;
        private double[][] energy;
        public MyGraph(boolean horizontal, double[][] energy) {
            this.horizontal = horizontal;
            this.energy = energy;

        }
        public Collection<Edge<MyVertex>> outgoingEdgesFrom(MyVertex v) {
            Set<Edge<MyVertex>> s = new HashSet<>();
            int width = energy.length;
            int height = energy[0].length;
            if (v.getX()==-2 && v.getY()==-2) {
                if (horizontal) {
                    for (int i = 0; i < height; i++) {
                        s.add(new Edge<>(v, new MyVertex(0, i), energy[0][i]));
                    }
                } else {
                    for (int i = 0; i < width; i++) {
                        s.add(new Edge<>(v, new MyVertex(i, 0), energy[i][0]));
                    }
                }
                return s;
            }
            if (horizontal && v.getX()== width-1) {
                s.add(new Edge<>(v, new MyVertex(-3, -3), 0.0));
                return s;
            } else if (!horizontal && v.getY()==height-1) {
                s.add(new Edge<>(v, new MyVertex(-3, -3), 0.0));
                return s;
            }
            int x = v.getX();
            int y = v.getY();
            //EnergyFunction ef = new DualGradientEnergyFunction();
            if (y>=0 && y<height-1 && !horizontal) {
                // double extra = 0;
                // if (v.getY()==0) {
                //     extra = energy[x][y];
                // } else {
                //     extra = 0;
                // }
                s.add(new Edge<>(v, new MyVertex(x, y + 1), energy[x][y+1]));
                if (x>0) {
                    s.add(new Edge<>(v, new MyVertex(x - 1, y + 1), energy[x-1][y+1]));
                }
                if (x<width-1) {
                    s.add(new Edge<>(v, new MyVertex(x + 1, y + 1), energy[x+1][y+1]));
                }
            }
            if (x>=0 &&  x<width-1 && horizontal) {
                // double extra = 0;
                // if (v.getX()==0) {
                //     extra = energy[x][y];
                // } else {
                //     extra = 0;
                // }
                s.add(new Edge<>(v, new MyVertex(x+1, y), energy[x+1][y]));
                if (y>0) {
                    s.add(new Edge<>(v, new MyVertex(x+1, y-1), energy[x+1][y-1]));
                }
                if (y<height-1) {
                    s.add(new Edge<>(v, new MyVertex(x + 1, y + 1), energy[x+1][y+1]));
                }
            }
            return s;
        }

    }




    private final ShortestPathFinder<Graph<MyVertex, Edge<MyVertex>>, MyVertex, Edge<MyVertex>> pathFinder;

    public DijkstraSeamFinder() {
        this.pathFinder = createPathFinder();
        //this.picture = new Picture();
    }

    protected <G extends Graph<V, Edge<V>>, V> ShortestPathFinder<G, V, Edge<V>> createPathFinder() {
        /*
        We override this during grading to test your code using our correct implementation so that
        you don't lose extra points if your implementation is buggy.
        */
        return new DijkstraShortestPathFinder<>();
    }

    @Override
    public List<Integer> findHorizontalSeam(double[][] energies) {
        if (energies == null||energies.length==0||energies[0].length==0) {
            return new ArrayList<>();
        }
        int width = energies.length;
        //int height = energies[0].length;
        //double min = Double.MAX_VALUE;
        Graph<MyVertex, Edge<MyVertex>> g = new MyGraph(true, energies);
        //int width = picture.width();
        //int height = picture.height();
        // for (int i = 0; i < height; i++) {
        //     for (int j = 0; j < height; j++) {
        ShortestPath<MyVertex, Edge<MyVertex>> temp =pathFinder.findShortestPath(g,
            new MyVertex(-2, -2), new MyVertex(-3, -3));
        //}
        List<MyVertex> l = temp.vertices();
        List<Integer> result = new ArrayList<>(width);
        l.remove(0);
        l.remove(l.size()-1);
        if (!l.isEmpty()) {
            for (MyVertex mv : l) {
                result.add(mv.getY());
            }
        }
        return result;
    }

    @Override
    public List<Integer> findVerticalSeam(double[][] energies) {
        if (energies == null||energies.length==0||energies[0].length==0) {
            return new ArrayList<>();
        }
        //int width = energies.length;
        int height = energies[0].length;
        Graph<MyVertex, Edge<MyVertex>> g = new MyGraph(false, energies);
        //double min = Double.MAX_VALUE;
        //List<MyVertex> l = new ArrayList<>();
        //int width = picture.width();
        //int height = picture.height();

        // for (int i = 0; i < width; i++) {
        //     for (int j = 0; j < width; j++) {
        //         if (i==0&&j==width-1 || i==width-1&&j==0) {
        //             continue;
        //         }
        //         ShortestPath<MyVertex, Edge<MyVertex>> temp =pathFinder.findShortestPath(g,
        //             new MyVertex(i, 0), new MyVertex(j, height - 1));
        //         if (temp==null) {
        //             continue;
        //         }
        //         //System.out.println(temp.totalWeight());
        //         if (min>temp.totalWeight()) {
        //             l.clear();
        //             l = temp.vertices();
        //             min = temp.totalWeight();
        //         }
        //
        //     }
        // }
        // List<Integer> result = new ArrayList<>(height);
        // if (!l.isEmpty()) {
        //     for (MyVertex mv : l) {
        //         result.add(mv.getX());
        //     }
        // }
        ShortestPath<MyVertex, Edge<MyVertex>> temp =pathFinder.findShortestPath(g,
            new MyVertex(-2, -2), new MyVertex(-3, -3));
        //}
        List<MyVertex> l = temp.vertices();
        List<Integer> result = new ArrayList<>(height);
        l.remove(0);
        l.remove(l.size()-1);
        if (!l.isEmpty()) {
            for (MyVertex mv : l) {
                result.add(mv.getX());
            }
        }
        return result;
    }
}
