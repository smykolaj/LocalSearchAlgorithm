import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main
{
    public static int[] weights = {
            3, 1, 6, 10, 1, 4, 9, 1, 7, 2, 6, 1, 6, 2, 2, 4, 8, 1, 7, 3, 6, 2, 9, 5, 3, 3, 4, 7, 3, 5, 30, 50
    };
    public static int[] values = {
            7, 4, 9, 18, 9, 15, 4, 2, 6, 13, 18, 12, 12, 16, 19, 19, 10, 16, 14, 3, 14, 4, 15, 7, 5, 10, 10, 13, 19, 9, 8, 5
    };
    public static final int size = 32;
    public static final int wMax = 75;
    public static final int iterations = 100000;

    public static class Solution
    {
        public String items;
        public int totalValue;
        public int totalWeight;

        public Solution()
        {
            items = "0".repeat(size);
        }

        public Solution(String items)
        {
            this.items = items;
            evaluateSolution();
        }

        public void evaluateSolution()
        {
            totalValue = 0;
            totalWeight = 0;
            for (int i = 0; i < size; i++)
            {
                if (items.charAt(i) == '1')
                {
                    totalWeight += weights[i];
                    totalValue += values[i];
                }
            }
        }
    }

    public static Solution generateInitialSolution()
    {
        Random rand = new Random();
        while (true)
        {
            StringBuilder sol = new StringBuilder();
            sol.append("0".repeat(size));
            int itemCount = rand.nextInt(size + 1);
            for (int i = 0; i < itemCount; i++)
            {
                int index = rand.nextInt(size);
                sol.setCharAt(index, '1');
            }
            Solution initialSolution = new Solution(sol.toString());
            if (initialSolution.totalWeight <= wMax)
            {
                return initialSolution;
            }
        }
    }

    public static List<Solution> generateNeighbors(Solution currentSolution)
    {
        ArrayList<Solution> neighbors = new ArrayList<>();
        for (int i = 0; i < size; i++)
        {
            if (currentSolution.items.charAt(i) == '0')
            {
                StringBuilder neighborItems = new StringBuilder(currentSolution.items);
                neighborItems.setCharAt(i, '1');
                Solution neighbor = new Solution(neighborItems.toString());
                if (neighbor.totalWeight <= wMax)
                {
                    neighbors.add(neighbor);
                }
            }
        }
        return neighbors;
    }

    public static Solution hillClimbing()
    {
        Solution current = generateInitialSolution();
        while (true)
        {
            List<Solution> neighbors = generateNeighbors(current);
            Solution bestNeighbor = current;
            for (Solution neighbor : neighbors)
            {
                if (neighbor.totalValue > bestNeighbor.totalValue)
                {
                    bestNeighbor = neighbor;
                }
            }
            if (bestNeighbor.totalValue == current.totalValue)
            {
                break;
            }
            current = bestNeighbor;
        }
        return current;
    }

    public static void main(String[] args)
    {
        Solution bestOverallSolution = null;

        for (int i = 0; i < iterations; i++)
        {
            Solution currentSolution = hillClimbing();
            if (bestOverallSolution == null || currentSolution.totalValue > bestOverallSolution.totalValue)
            {
                bestOverallSolution = currentSolution;
            }
        }

        System.out.println("Best subset: \n" + bestOverallSolution.items);
        System.out.println("Total weight: " + bestOverallSolution.totalWeight);
        System.out.println("Total value: " + bestOverallSolution.totalValue);
    }
}
