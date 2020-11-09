package com.draw;

import com.draw.commands.Command;
import com.draw.core.Canvas;
import com.draw.exception.CanvasException;
import com.draw.exception.CanvasQuitException;
import com.draw.factory.DrawFactory;
import com.draw.factory.DrawFactoryImpl;
import com.draw.util.ApplicationUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Closeable;
import java.util.Scanner;

import static com.draw.util.ApplicationUtil.CANVAS_MAX_HEIGHT;
import static com.draw.util.ApplicationUtil.CANVAS_MAX_WIDTH;

/**
 * Main class for starting the application. Tests the application in an interactive mode.
 */
public class Application {

    private static final transient Log logger = LogFactory.getLog(Application.class);
    private Scanner scanner;
    private DrawFactory drawFactory;

    public Application() {
        drawFactory = new DrawFactoryImpl();
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        try {
            if (args.length > 0) {
                CANVAS_MAX_WIDTH = Integer.parseInt(args[0]);
                CANVAS_MAX_HEIGHT = Integer.parseInt(args[1]);
            }
        }catch (Exception e){
            logger.error("Invalid Canvas height and width parameters provided.");
        }finally {
            System.out.println("Invalid Canvas Height and Width parameters provided, set to default 200 x 50");
            CANVAS_MAX_HEIGHT = 50;
            CANVAS_MAX_WIDTH = 200;
        }
        Application application = new Application();
        application.run();
    }

    private void run() {
        boolean isExit = false;
        Canvas canvas = null;
        try {
            canvas = new Canvas(0, 0);
        } catch (CanvasException e) {
            logger.error("Exception initializing Canvas: " + e.getMessage(), e);
            System.out.println("Exception initializing Canvas: " + e.getMessage());
        }

        do {
            try {
                System.out.print("Insert command: ");
                String line = scanner.nextLine();
                Command command = drawFactory.buildCommand(line, canvas);
                command.execute();
                canvas = command.getCanvas();
                canvas.printCanvas();
            } catch (CanvasQuitException e) {
                isExit = true;
                System.out.println("Thank you! Bye.");
            } catch (CanvasException e) {
                logger.error("Canvas Exception: " + e.getMessage(), e);
                System.out.println("Canvas Exception: " + e.getMessage());
                System.out.println("Insert '" + ApplicationUtil.CMD_HELP + "' for Help.\n");
            } catch (Exception e) {
                logger.error("General Exception: " + e.getMessage(), e);
                System.out.println("General Exception: " + e.getMessage());
                System.out.println("Insert '" + ApplicationUtil.CMD_HELP + "' for Help.\n");
            }
        } while (!isExit);

        close(scanner);
    }

    /**
     * Close the Scanner
     */
    private static void close(Closeable closeable) {
        try {
            closeable.close();
        } catch (Exception e) {
            logger.error("Error closing the console: " + e.getMessage(), e);
            System.out.println("Cannot close the console! e: " + e.getMessage());
        }
    }

}
