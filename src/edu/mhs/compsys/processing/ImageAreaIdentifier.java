package edu.mhs.compsys.processing;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageAreaIdentifier
{

	// WARNINGZZZZZZ - DONT USE THIS CLASS
	// WARNINGZZZZZZ - DONT USE THIS CLASS
	// WARNINGZZZZZZ - DONT USE THIS CLASS
	// WARNINGZZZZZZ - DONT USE THIS CLASS
	// WARNINGZZZZZZ - DONT USE THIS CLASS
	// WARNINGZZZZZZ - DONT USE THIS CLASS
	// WARNINGZZZZZZ - DONT USE THIS CLASS
	// WARNINGZZZZZZ - DONT USE THIS CLASS
	// WARNINGZZZZZZ - DONT USE THIS CLASS
	/**
	 * @param i
	 * @return
	 */
	public static BufferedImage getColoredAreas(BinaryImage i)
	{
		BufferedImage ret = new BufferedImage(i.getWidth(), i.getHeight(), BufferedImage.TYPE_INT_RGB);
		int areaColor = Color.red.getRGB();
		int tempAreaColor = 0 + areaColor;
		// For loop count VI

		for (int setX = 0; setX < i.getWidth(); setX++)
		{
			for (int setY = 0; setY < i.getHeight(); setY++)
			{
				ret.setRGB(setX, setY, 0);
			}
		}

		for (int passes = 0; passes < 1; passes++)
		{
			for (int x = 1; x < i.getWidth() - 1; x++)
			{
				for (int y = 1; y < i.getHeight() - 1; y++)
				{
					if (i.get(x, y))// a change is detected
					{
						boolean[][] neighbors = new boolean[3][3];
						boolean foundOldColor = false;
						for (int xx = 0; xx < 3; xx++)
						{
							for (int yy = 0; yy < 3; yy++)
							{
								neighbors[xx][yy] = i.get(x - 1 + xx, y - 1 + yy);
								if (ret.getRGB(x - 1 + xx, y - 1 + yy) > 0)
								{
									foundOldColor = true;
									tempAreaColor = ret.getRGB(x - 1 + xx, y - 1 + yy);
								}
							}
						}

						if (!foundOldColor)
						{
							areaColor++;
							tempAreaColor = 0 + areaColor;
						}

						for (int paintX = -1; paintX < 2; paintX++)
						{
							for (int paintY = -1; paintY < 2; paintY++)
							{
								if (neighbors[1 + paintX][1 + paintY])
								{
									ret.setRGB(x + paintX, y + paintY, tempAreaColor);
								}
							}
						}

					}
				}
			}
		}

		return ret;
	}
	/*
	 * linked = [] labels = structure with dimensions of data, initialized with
	 * the value of Background
	 * 
	 * First pass
	 * 
	 * for row in data: for column in row: if data[row][column] is not
	 * Background
	 * 
	 * neighbors = connected elements with the current element's value
	 * 
	 * if neighbors is empty linked[NextLabel] = set containing NextLabel
	 * labels[row][column] = NextLabel NextLabel += 1
	 * 
	 * else
	 * 
	 * Find the smallest label
	 * 
	 * L = neighbors labels labels[row][column] = min(L) for label in L
	 * linked[label] = union(linked[label], L)
	 * 
	 * Second pass
	 * 
	 * for row in data for column in row if data[row][column] is not Background
	 * labels[row][column] = find(labels[row][column])
	 * 
	 * return labels
	 */
}
