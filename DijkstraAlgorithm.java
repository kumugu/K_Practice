import java.util.*;

class DijkstraAlgorithm {
    // Edge 클래스
    static class Edge {
        int target;
        int weight;

        public Edge(int target, int weight) {
            this.target = target;
            this.weight = weight;
        }
    }

    // 그래프 클래스
    static class Graph {
        private final int vertices; // 정점 수
        private final List<List<Edge>> adjacencyList; // 인접 리스트

        public Graph(int vertices) {
            this.vertices = vertices;
            adjacencyList = new ArrayList<>(vertices);
            for (int i = 0; i < vertices; i++) {
                adjacencyList.add(new ArrayList<>());
            }
        }

        // 간선 추가
        public void addEdge(int source, int target, int weight) {
            adjacencyList.get(source).add(new Edge(target, weight));
            adjacencyList.get(target).add(new Edge(source, weight)); // 무향 그래프
        }

        // 다익스트라 알고리즘
        public void dijkstra(int start) {
            int[] distances = new int[vertices]; // 최단 거리 배열
            Arrays.fill(distances, Integer.MAX_VALUE); // 초기값 무한대
            distances[start] = 0; // 시작점의 거리는 0

            PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));
            pq.offer(new Edge(start, 0)); // 시작점 추가

            boolean[] visited = new boolean[vertices]; // 방문 여부

            while (!pq.isEmpty()) {
                Edge current = pq.poll();
                int currentVertex = current.target;

                if (visited[currentVertex]) continue; // 이미 방문한 경우 스킵
                visited[currentVertex] = true;

                for (Edge edge : adjacencyList.get(currentVertex)) {
                    int neighbor = edge.target;
                    int newDist = distances[currentVertex] + edge.weight;

                    if (newDist < distances[neighbor]) { // 더 짧은 경로 발견
                        distances[neighbor] = newDist;
                        pq.offer(new Edge(neighbor, newDist));
                    }
                }
            }

            // 결과 출력
            System.out.println("Shortest distances from vertex " + start + ":");
            for (int i = 0; i < vertices; i++) {
                System.out.println("To vertex " + i + ": " + (distances[i] == Integer.MAX_VALUE ? "Infinity" : distances[i]));
            }
        }
    }

    public static void main(String[] args) {
        // 그래프 생성
        int vertices = 6;
        Graph graph = new Graph(vertices);

        // 간선 추가 (예: source, target, weight)
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 3);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 2);
        graph.addEdge(2, 3, 4);
        graph.addEdge(3, 4, 2);
        graph.addEdge(4, 5, 6);

        // 다익스트라 알고리즘 실행
        graph.dijkstra(0); // 시작점: 0번 정점
    }
}
