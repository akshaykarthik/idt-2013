package edu.mhs.compsys.processing;

import java.awt.image.BufferedImage;

public class ImageAreaIdentifier

{

	/**
	 * @param i
	 * @return
	 */
	public static BufferedImage getAreas(BinaryImage i)
	{

		BufferedImage ret = new BufferedImage(i.getWidth(), i.getHeight(), BufferedImage.TYPE_INT_RGB);
		int areaColor = 1;
		int tempAreaColor = 1;

		// For loop count VI
		for (int passes = 0; passes < 1; passes++)
		{
			for (int x = 0; x < i.getWidth(); x++)
			{
				for (int y = 0; y < i.getHeight(); y++)
				{
					if (i.get(x, y))
					{
						boolean[][] neighbors = new boolean[3][3];

						for (int xx = 0; xx < 3; xx++)
						{
							for (int yy = 0; yy < 3; yy++)
							{
								neighbors[xx][yy] = i.get(x - 2 + xx, y - 2 + yy);
							}
						}
						for (int xx = 0; xx < 3; xx++)
						{
							for (int yy = 0; yy < 3F; yy++)
							{
								if (neighbors[xx][yy])
								{
									if (ret.getRGB(xx, yy) == 0)// starting new
																// color area
									{
										tempAreaColor = areaColor + 1;
										areaColor++;
										ret.setRGB(xx, yy, tempAreaColor);
									}
									else
									// found old color area and using that color
									{
										tempAreaColor = ret.getRGB(xx, yy);
										ret.setRGB(xx, yy, tempAreaColor);
									}
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
