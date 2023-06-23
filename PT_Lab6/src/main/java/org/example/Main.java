package org.example;

import org.apache.commons.lang3.tuple.Pair;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
	private static final String SRC = "./src/main/resources/images/";
	private static final String DST = "./src/main/resources/output/";
	private static int changePalette(BufferedImage original, int x, int y) {
		int rgb = original.getRGB(x, y);
		Color color = new Color(rgb);
		int gray = (int)(0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue());
		Color result = new Color(gray, gray, gray); // RED AND BLUE AND GREEN
		return result.getRGB();
	}
	private static BufferedImage transform(BufferedImage original) {
		BufferedImage image = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());

		for (int i = 0; i < original.getHeight(); ++i)
		{
			for (int j = 0; j < original.getWidth(); ++j) {
				int rgb = changePalette(original, j, i);
				image.setRGB(j, i, rgb);
			}
		}

		return image;
	}
	private static BufferedImage loadImage(Path source) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(source.toFile());
		}
		catch (IOException ex) {
			System.out.println("Failed to load the image " + source.toString());
		}
		return image;
	}
	private static Pair<String, BufferedImage> makePair(Path source) {
		String filename = source.getFileName().toString();
		BufferedImage image = loadImage(source);
		return Pair.of(filename, image);
	}
	private static Pair<String, BufferedImage> getOutputPair(Pair<String, BufferedImage> input) {
		Pair<String, BufferedImage> output;
		output = Pair.of(input.getLeft(), transform(input.getRight()));
		return output;
	}
	private static void saveImage(Pair<String, BufferedImage> input) {
		try {
			File output = new File(DST + input.getLeft());
			ImageIO.write(input.getRight(), "jpg", output);
			System.out.println(input.getLeft() + " saved successfully!");
		}
		catch (IOException ex) {
			System.out.println("Failed to save the image " + input.getLeft().toString());
		}
	}
	public static void main(String[] args) {
		int poolSize = 1;
		if (args[0] != null) {
			poolSize = Integer.parseInt(args[0]);
		}
		ForkJoinPool pool = new ForkJoinPool(poolSize);
		System.out.println(poolSize);

		List<Path> files = null;
		Path source = Path.of(SRC);
		long start = System.currentTimeMillis();
		try (Stream<Path> stream = Files.list(source)) {

			files = stream.collect(Collectors.toList());
			List<Path> finalFiles = files;

			pool.submit(() -> {
				Stream<Path> input = finalFiles.stream().parallel();
				Stream<Pair<String, BufferedImage>> inputPairStream = input
						.map(path -> makePair(path));
				Stream<Pair<String, BufferedImage>> outputPairStream = inputPairStream
						.map(pair -> getOutputPair(pair));
				outputPairStream.forEach(pair -> saveImage(pair));
			}).get();
		}
		catch (IOException | InterruptedException | ExecutionException ex) {
			System.out.println("Failed to get the files! Details:\n" + ex.toString());
			return;
		}
		long finish = System.currentTimeMillis();
		System.out.println("\n\nConversion finished successfully!");
		System.out.println("Total running time: " + (finish - start) + " ms.");
		System.out.println("Pool size: " + poolSize + " threads.");
	}
}