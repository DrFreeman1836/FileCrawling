import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) throws IOException {

    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.print("Введите путь к папке: ");
      String input = scanner.nextLine();

      if (input.equals("break")) {
        break;
      }

      if (Files.isDirectory(Path.of(input))) {
        StringBuilder stringBuilder = new StringBuilder();
        Files.walk(Path.of(input)).filter(Files::isRegularFile)
            .filter(f -> f.getFileName().toString().contains(".txt"))
            .sorted(Comparator.comparing(Path::getFileName))
            .forEach(f -> stringBuilder.append(readFile(f)));

        System.out.print("Куда сохранить: ");
        input = scanner.nextLine();
        if (toFile(input, stringBuilder.toString())) {
          System.out.println("Файл сохранен");
        } else {
          System.out.println("Файл не сохранен");
        }

      } else {
        System.out.println("Указан неверный путь");
      }
    }

  }

  public static String readFile(Path path) {
    StringBuilder builder = new StringBuilder();
    try {
      List<String> lines = Files.readAllLines(path);
      for (String line : lines) {
        builder.append(line).append("\n");
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return builder.toString();
  }

  public static boolean toFile(String path, String text) {
    try {
      PrintWriter writer = new PrintWriter(new FileWriter(path + "/result.txt"));
      writer.write(text);
      writer.flush();
      writer.close();
    } catch (IOException ex) {
      ex.printStackTrace();
      return false;
    }
    return true;
  }

}
