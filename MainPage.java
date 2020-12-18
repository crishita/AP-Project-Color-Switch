import java.io.Serializable;
import javafx.scene.paint.Color;
import javafx.scene.robot.Robot;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.animation.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class MainPage extends Application implements Serializable{ //Composite Design Pattern
	private static long serialVersionUID=30L;
	private transient Stage window;
	private final double windowHeight = 690;
	private final double windowWidth = 400;
	private MainPage deserializeMenu;
	private Game gamePlay;
	private int TotalGamePlayed;
	private ArrayList<Game> SavedGames;
	private int HighestScore;
	private int TotalStarsCollected;
	private int TotalAvailableStars;
	private transient MediaPlayer player;

	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) {
		try { 
			try {
				this.deserializeMenu= this.deserialize();
			} catch(Exception e){
				this.deserializeMenu=this;
				this.deserializeMenu.initialise();
			}
			this.deserializeMenu.window=primaryStage;
			this.deserializeMenu.window.getIcons().add(new Image("color-switch-icon.png"));
			this.deserializeMenu.window.setTitle("COLOR SWITCH");
			this.deserializeMenu.window.setScene(this.deserializeMenu.getFirstScene());
			this.deserializeMenu.window.show();
			this.deserializeMenu.window.setOnCloseRequest(event -> {
				try {
					deserializeMenu.serialize();
				} catch(Exception ex){
					ex.printStackTrace();
				}
			});
			Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2000), e -> this.deserializeMenu.window.setScene(this.deserializeMenu.getSecondScene())));
			timeline.play();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void serialize() throws FileNotFoundException,IOException{
		ObjectOutputStream out=null;
		try {
			out=new ObjectOutputStream(new FileOutputStream("MainPage.txt")); //Decorator Design pattern
			out.writeObject(this.deserializeMenu);
		}finally{
			out.close();
		}
	}
	
	public MainPage deserialize() throws FileNotFoundException, IOException, ClassNotFoundException{
		ObjectInputStream in=null;
		MainPage menu=null;
		try {
			in=new ObjectInputStream(new FileInputStream("MainPage.txt")); //Decorator Design Pattern
			menu=(MainPage) in.readObject();
		} finally {
			in.close();
		}
		return menu;
	}
	
	public void initialise() {
		this.TotalGamePlayed=0;
		this.HighestScore=0;
		this.SavedGames= new ArrayList<Game>();
	}
	
	public Stage getWindow() {
		return this.deserializeMenu.window;
	}
	
	public double getWindowWidth() {
		return this.deserializeMenu.windowWidth;
	}
	
	public double getWindowHeight() {
		return this.deserializeMenu.windowHeight;
	}
	
	public void addToSavedGames(Game game) {
		this.deserializeMenu.SavedGames.add(game);
	}
	
	public int getHighestScore() {
		return this.deserializeMenu.HighestScore;
	}
	
	public int getTotalStarsCollected() {
		return this.deserializeMenu.TotalStarsCollected;
	}
	
	public void setTotalStarsCollected(int n) {
		this.deserializeMenu.TotalStarsCollected=n;
	}
	
	public void setHighestScore(int n) {
		this.deserializeMenu.HighestScore=n;
	}
	
	public ArrayList<Game> getSavedGames(){
		return this.SavedGames;
	}
	
	public RotateTransition rotateRing(ImageView image, boolean flag, int speed) {
		RotateTransition rotateTransition = new RotateTransition();
		rotateTransition.setDuration(Duration.millis(speed)); 
		rotateTransition.setNode(image);
		rotateTransition.setByAngle(360); 
		rotateTransition.setCycleCount(Animation.INDEFINITE); 
		rotateTransition.setAutoReverse(flag); 
		rotateTransition.play(); 
		return rotateTransition;
	}
	
	public RotateTransition rotateGroup(Group root, boolean flag, int speed) {
		RotateTransition rotateTransition = new RotateTransition();
		rotateTransition.setDuration(Duration.millis(speed)); 
		rotateTransition.setNode(root);
		rotateTransition.setByAngle(360); 
		rotateTransition.setCycleCount(Animation.INDEFINITE); 
		rotateTransition.setAutoReverse(flag); 
		rotateTransition.play(); 
		return rotateTransition;
	}
	
	public ScaleTransition scaleImageAnimation(ImageView image, boolean flag, double speed, double x) {
		ScaleTransition scaletransition=new ScaleTransition(Duration.seconds(speed),image);
		scaletransition.setCycleCount(Animation.INDEFINITE); 
		scaletransition.setToX(x);
		scaletransition.setToY(x);
		scaletransition.setAutoReverse(flag); 
		scaletransition.play(); 
		return scaletransition;
	}
	
	public ScaleTransition scaleButtonAnimation(Button button, boolean flag, double speed, double x) {
		ScaleTransition scaletransition=new ScaleTransition(Duration.seconds(speed),button);
		scaletransition.setCycleCount(Animation.INDEFINITE); 
		scaletransition.setToX(x);
		scaletransition.setToY(x);
		scaletransition.setAutoReverse(flag); 
		scaletransition.play(); 
		return scaletransition;
	}
	
	public Scene getFirstScene() {
		Image image = new Image("Title.png");
		ImageView imageView = new ImageView(image);
		imageView.setX(0); 
	    imageView.setY(200); 
	    imageView.setFitHeight(300); 
	    imageView.setFitWidth(550); 
	    imageView.setPreserveRatio(true);
	    this.deserializeMenu.scaleImageAnimation(imageView, true, 1.1, 0.5);
	    String uriString = new File("melody.mp3").toURI().toString();
	    this.deserializeMenu.player = new MediaPlayer(new Media(uriString));
	    this.deserializeMenu.player.setAutoPlay(true);
	    this.deserializeMenu.player.setCycleCount(MediaPlayer.INDEFINITE);
	    Group root = new Group(imageView);  
	    Scene scene = new Scene(root,this.deserializeMenu.windowWidth,this.deserializeMenu.windowHeight);
	    scene.setFill(Color.BLACK);
	    return scene;
	}
	
	public Scene getSecondScene() {
		Image image = new Image("color-switch-icon.png");
		ImageView imageView = new ImageView(image);
		imageView.setX(50); 
	    imageView.setY(200); 
	    imageView.setFitHeight(300); 
	    imageView.setFitWidth(550); 
	    imageView.setPreserveRatio(true); 
	    RotateTransition rotateTransition = this.deserializeMenu.rotateRing(imageView, false, 1000);
	    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2000), e -> rotateTransition.stop()));
		timeline.play();
		Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(2000), e -> this.deserializeMenu.displayMenuOptions()));
		timeline2.play();
	    Group root = new Group(imageView);  
	    Scene scene = new Scene(root,this.deserializeMenu.windowWidth,this.deserializeMenu.windowHeight);
	    scene.setFill(Color.web("#272727"));
	    return scene;
	}
	
	
	public Button startSound() {
		Button play = new Button();
		play.setStyle(
	            "-fx-background-color:	#FBFBF8; " +
	            "-fx-background-radius: 20; "+  
	            "  -fx-border-radius: 20;"
	    );
		play.setLayoutX(180);
		play.setLayoutY(250);
		play.setPrefHeight(60);
		play.setPrefWidth(60);
		Image cameraIcon = new Image("unmute.png");
		ImageView cameraIconView = new ImageView(cameraIcon);
		cameraIconView.setFitHeight(35);
		cameraIconView.setFitWidth(35);
		play.setGraphic(cameraIconView);

		EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() { 
			@Override
			public void handle(MouseEvent e) 
			{ 
				deserializeMenu.player.play();
			} 
		}; 
		this.deserializeMenu.shadowEffect(play);
		play.setOnMouseClicked(event);
		return play;
	}
	
	public Button pauseSound() {
        Button resume = new Button();
        resume.setStyle(
	            "-fx-background-color:	#FBFBF8; " +
	            "-fx-background-radius: 20; "+  
	            "  -fx-border-radius: 20;"
	    );
        resume.setLayoutX(180);
        resume.setLayoutY(340);
        resume.setPrefHeight(60);
        resume.setPrefWidth(60);
        Image mute = new Image("mute.png");
    	ImageView muteIconView = new ImageView(mute);
    	muteIconView.setFitHeight(35);
    	muteIconView.setFitWidth(35);
    	resume.setGraphic(muteIconView);
    	EventHandler<MouseEvent> even = new EventHandler<MouseEvent>() { 
    		@Override
            public void handle(MouseEvent e) 
            { 
    			deserializeMenu.player.pause();
            } 
        }; 
        this.deserializeMenu.shadowEffect(resume);
    	resume.setOnMouseClicked(even);
    	return resume;  
	}
	
	public void SoundPage() {
		Group root = new Group();
		Scene scene = new Scene(root, this.deserializeMenu.windowWidth, this.deserializeMenu.windowHeight);
	    scene.setFill(Color.web("#282828"));
		TextFlow text_flow = new TextFlow();
		text_flow.setLayoutX(0);
		text_flow.setLayoutY(60);
		text_flow.setPrefWidth(400);
		text_flow.setPrefHeight(100);
		Text text_1 = new Text("\n\n Sound Setting \n\n\n\n\n");
		text_1.setFill(Color.web("#FAE100"));   
	    text_1.setStrokeWidth(1.5); 
	    text_1.setStroke(Color.PINK);  
		text_1.setFont(Font.font("Verdana", FontWeight.BOLD,40));
		text_flow.setTextAlignment(TextAlignment.CENTER);
		Glow glow = new Glow(); 
		glow.setLevel(0.8);  
		text_1.setEffect(glow); 
		text_flow.getChildren().add(text_1);
		Button mainMenu=this.deserializeMenu.BackToMainMenu();
		mainMenu.setLayoutX(180);
	    mainMenu.setLayoutY(550);
	    root.getChildren().addAll(text_flow, mainMenu, this.deserializeMenu.startSound(),this.deserializeMenu.pauseSound());
	    this.deserializeMenu.window.setTitle("Sound Settings");
	    this.deserializeMenu.window.setScene(scene);
	}
	
	public Button soundOption() {
		Image cameraIcon = new Image("setting.png");
		ImageView cameraIconView = new ImageView(cameraIcon);
		cameraIconView.setFitHeight(30);
		cameraIconView.setFitWidth(30);
		Button button= new Button();
		button.setGraphic(cameraIconView);
		button.setPrefHeight(50);
		button.setPrefWidth(50);
		button.setStyle(
	            "-fx-background-color:	#606060; " +
	            "-fx-background-radius: 50; "+  
	            "  -fx-border-radius: 50;"
	    );
		EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() { 
			@Override
            public void handle(MouseEvent e) 
            { 
				deserializeMenu.SoundPage();
            } 
        }; 
        this.deserializeMenu.shadowEffect(button);
		button.setOnMouseClicked(event);
		return button;
	}
		
	public void displayMenuOptions() {
		Image image = new Image("color.png");
		ImageView imageView = new ImageView(image);
		imageView.setX(130); 
	    imageView.setY(20); 
	    imageView.setFitHeight(100); 
	    imageView.setFitWidth(150); 
	    imageView.setPreserveRatio(true); 
	    Group root = new Group(imageView); 
		Scene scene = new Scene(root,this.deserializeMenu.windowWidth,this.deserializeMenu.windowHeight);
		this.deserializeMenu.setMainHeading(root, 20);
		Button helpButton= this.deserializeMenu.helpOption();
	    root.getChildren().add(helpButton);
	    helpButton.setLayoutX(245);
	    helpButton.setLayoutY(600);
	    scene.setFill(Color.web("#282828"));
	    Button newButton=this.deserializeMenu.newGameOption(root);
	    root.getChildren().add(newButton);
	    newButton.setLayoutX(150);
	    newButton.setLayoutY(176);
	    Button resumeButton= this.deserializeMenu.resumeOption(root);
	    root.getChildren().add(resumeButton);
	    resumeButton.setLayoutX(146);
	    resumeButton.setLayoutY(422);
	    Button exitButton= this.deserializeMenu.exit();
	    root.getChildren().add(exitButton);
	    exitButton.setLayoutX(175);
	    exitButton.setLayoutY(600);
	    Button soundButton =this.deserializeMenu.soundOption();
	    soundButton.setLayoutX(100);
	    soundButton.setLayoutY(600);
	    root.getChildren().add(soundButton);
	    this.deserializeMenu.window.setScene(scene);
	    this.deserializeMenu.window.setTitle("COLOR SWITCH");
	}
	
	public void setMainHeading(Group root, int y) {
		Image image = new Image("ring.png");
		ImageView imageView = new ImageView(image);
		ImageView imageView2 = new ImageView(image);
		imageView.setX(168); 
	    imageView.setY(y); 
	    imageView.setFitHeight(63); 
	    imageView.setFitWidth(30); 
	    imageView2.setX(217); 
	    imageView2.setY(y); 
	    imageView2.setFitHeight(63); 
	    imageView2.setFitWidth(30); 
	    imageView2.setPreserveRatio(true);
	    imageView.setPreserveRatio(true);
	    this.deserializeMenu.rotateRing(imageView, false, 1000);
	    this.deserializeMenu.rotateRing(imageView2, false, 1000);
	    root.getChildren().add(imageView);
	    root.getChildren().add(imageView2);
	}
	
	public Button helpOption() {
		Image cameraIcon = new Image("help.png");
		ImageView cameraIconView = new ImageView(cameraIcon);
		cameraIconView.setFitHeight(35);
		cameraIconView.setFitWidth(26);
		Button button= new Button();
		button.setGraphic(cameraIconView);
		button.setPrefHeight(50);
		button.setPrefWidth(50);
		button.setStyle(
	            "-fx-background-color:	#606060; " +
	            "-fx-background-radius: 50; "+  
	            "  -fx-border-radius: 50;"
	    );
		EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() { 
			@Override
            public void handle(MouseEvent e) 
            { 
				deserializeMenu.displayGameRules();
            } 
        }; 
        this.deserializeMenu.shadowEffect(button);
		button.setOnMouseClicked(event);
		return button;
	}
	
	public void displayGameRules() {
		Image image = new Image("rhombus.png");
		ImageView imageView = new ImageView(image);
		imageView.setX(3); 
	    imageView.setY(140); 
	    imageView.setFitHeight(400); 
	    imageView.setFitWidth(400);
	    this.deserializeMenu.scaleImageAnimation(imageView, true, 1, 1.1);
		TextFlow text_flow = new TextFlow();
		text_flow.setLayoutX(50);
		text_flow.setLayoutY(210);
		text_flow.setPrefWidth(300);
		Text text_1 = new Text("Follow the given instructions: \r\n\n" + 
				"The main aim of the game is to collect stars. The more the stars are collected, the more points are earned by user\r\n" + 
				"\r\n" + 
				"When the ball touches the obstacle, the colour of ball and colour of obstacles must be same otherwise the game will end.\n ");
		text_1.setFill(Color.WHITE);
		Text text_2 = new Text("\n\nReady, Steady and GO!!!!!");
		text_2.setFill(Color.WHITE);
		text_2.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
		Group root = new Group();   
		Scene scene = new Scene(root,this.deserializeMenu.windowWidth,this.deserializeMenu.windowHeight);
		scene.setFill(Color.BLACK);
        text_1.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        text_flow.setTextAlignment(TextAlignment.CENTER);
        text_flow.getChildren().addAll(text_1,text_2);
        Button mainMenu= this.deserializeMenu.BackToMainMenu();
        root.getChildren().addAll(imageView, text_flow, mainMenu); 
        mainMenu.setLayoutX(20);
        mainMenu.setLayoutY(20);
        this.deserializeMenu.window.setScene(scene);
        this.deserializeMenu.window.setTitle("Instructions");  
	}
	
	public Button newGameOption(Group root) {
		Image image = new Image("outerRing.png");
		ImageView imageView = new ImageView(image);
		imageView.setPreserveRatio(true);
		imageView.setX(95); 
	    imageView.setY(123); 
	    imageView.setFitHeight(210); 
	    imageView.setFitWidth(210); 
		this.deserializeMenu.rotateRing(imageView, true, 1200);
		root.getChildren().add(imageView);
		Button button=new Button();
		button.setText("New Game");
		button.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		button.setPrefHeight(100);
		button.setPrefWidth(100);
		button.setStyle(
	            "-fx-background-color: #505050; " +
	            "-fx-background-radius: 50; "+  
	            "  -fx-border-radius: 50;"+
	            "-fx-text-fill: white"
	    );
		EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() { 
			@Override
            public void handle(MouseEvent e) 
            { 
				deserializeMenu.New_Game();
            } 
        }; 
        this.deserializeMenu.shadowEffect(button);
		button.setOnMouseClicked(event);
		return button;
	}
	
	public void New_Game() {
		this.deserializeMenu.TotalGamePlayed=this.deserializeMenu.TotalGamePlayed+1;
		this.deserializeMenu.gamePlay=null;
		System.gc();
		this.deserializeMenu.gamePlay = new Game(this.deserializeMenu.TotalGamePlayed, this.deserializeMenu);
	}
	
	public Button BackToMainMenu() {
		Image cameraIcon = new Image("home.png");
		ImageView cameraIconView = new ImageView(cameraIcon);
		cameraIconView.setFitHeight(35);
		cameraIconView.setFitWidth(35);
		Button button= new Button();
		button.setGraphic(cameraIconView);
		button.setPrefHeight(50);
		button.setPrefWidth(50);
		button.setStyle(
	            "-fx-background-color: 	#606060; " +
	            "-fx-background-radius: 50; "+  
	            "  -fx-border-radius: 50;"
	    );
		EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() { 
			@Override
            public void handle(MouseEvent e) 
            { 
				deserializeMenu.displayMenuOptions();
            } 
        }; 
        this.deserializeMenu.shadowEffect(button);
		button.setOnMouseClicked(event);
		return button;
	}
	
	public Button resumeOption(Group root) {
		Image image = new Image("outerRing.png");
		ImageView imageView = new ImageView(image);
		imageView.setPreserveRatio(true);
		imageView.setX(115); 
	    imageView.setY(393); 
	    imageView.setFitHeight(162); 
	    imageView.setFitWidth(162); 
		this.deserializeMenu.rotateRing(imageView, true, 1200);
		root.getChildren().add(imageView);
		Button button= new Button();
	    button.setText("Resume");
	    button.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		button.setPrefHeight(100);
		button.setPrefWidth(100);
		button.setStyle(
	            "-fx-background-color: #505050; " +
	            "-fx-background-radius: 50; "+  
	            "  -fx-border-radius: 50;" +
	            "-fx-text-fill: white"
	    );
		EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() { 
			@Override
            public void handle(MouseEvent e) 
            { 
				try {
					if(deserializeMenu.SavedGames.size()==0) {
						throw new NoSavedGameException("NO GAME SAVED YET!!");
					}
					else {
						deserializeMenu.Resume_Game();
					}
				} catch(NoSavedGameException f) {
					showMessageBox();
				}
            } 
        }; 
        this.deserializeMenu.shadowEffect(button);
		button.setOnMouseClicked(event);
		return button;
	}
	
	public void showMessageBox() {
		Alert a = new Alert(AlertType.NONE); 
		a.setAlertType(AlertType.INFORMATION); 
		ImageView star=new ImageView(new Image("star2.png"));
		star.prefHeight(70);
		star.prefWidth(70);
		this.deserializeMenu.scaleImageAnimation(star, true, 1, 0.5);
		a.getDialogPane().setStyle("-fx-background-color: #282828;");
		Stage s=(Stage)a.getDialogPane().getScene().getWindow();
		s.getIcons().add(new Image("color-switch-icon.png"));
		a.setGraphic(star);
		a.setHeaderText(null);
	    a.setTitle("OOPS SORRY!!");
	    String content = "You haven't saved any game yet. Proceed to New Game....";
	    int length = content.length();
	    Text text = new Text(10, 20, "");
	    Animation animation = new Transition() {
	    	{
	            setCycleDuration(Duration.millis(3500));
	        }
	    
	        protected void interpolate(double f) {
	            int n = Math.round(length * (float) f);
	            text.setText(content.substring(0, n));
	        }
	    
	    };
	    animation.play();
		text.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
		text.setFill(Color.WHITE);
		TextFlow textFlow=new TextFlow(text);
	    a.getDialogPane().setContent(textFlow);
	    a.getDialogPane().setPrefWidth(400);
	    a.getDialogPane().setPrefHeight(125);
		a.show();
	}
	
	public void Resume_Game() {
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.pannableProperty().set(true);
		scrollPane.setStyle("-fx-background-color: #282828;"+ "-fx-background: #282828;");
		scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.fitToHeightProperty().set(true);
		scrollPane.setLayoutY(20);
		scrollPane.setPrefHeight(this.deserializeMenu.windowHeight);
		scrollPane.setPrefWidth(this.deserializeMenu.windowWidth);
		Group root = new Group(); 
		Scene scene = new Scene(scrollPane,this.deserializeMenu.windowWidth,this.deserializeMenu.windowHeight);
		scene.setFill(Color.web("#282828"));
		Button homeMenu= this.deserializeMenu.BackToMainMenu();
        homeMenu.setLayoutX(20);
        homeMenu.setLayoutY(20);
        TextFlow text_flow = new TextFlow();
		text_flow.setLayoutX(0);
		text_flow.setLayoutY(60);
		text_flow.setPrefWidth(400);
		text_flow.setPrefHeight(100);
		Text text_1 = new Text("\n\nChoose the game to continue: \n\n\n\n\n");
		text_1.setFill(Color.web("#FAE100"));   
	    text_1.setStrokeWidth(1.5); 
	    text_1.setStroke(Color.PINK);  
		text_1.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		text_flow.setTextAlignment(TextAlignment.CENTER);
		text_flow.getChildren().add(text_1);
		root.getChildren().addAll(homeMenu, text_flow); 
		
		for(int i=1;i<=this.deserializeMenu.SavedGames.size();i++) {
			
			Button loadGame=this.deserializeMenu.addLoadGameButton(this.deserializeMenu.SavedGames.get(i-1), i);
	        loadGame.setLayoutX(100);
	        loadGame.setLayoutY(170 + ((i-1)*100));
	        root.getChildren().add(loadGame); 
		}
		scrollPane.setContent(root);
		this.deserializeMenu.window.setScene(scene);
	}
	
	public Button addLoadGameButton(Game g, int i) {
		Image cameraIcon = new Image("star.png");
		ImageView cameraIconView = new ImageView(cameraIcon);
		cameraIconView.setFitHeight(30);
		cameraIconView.setFitWidth(40);
		ParallelTransition parallel= new ParallelTransition(cameraIconView,this.deserializeMenu.rotateRing(cameraIconView, false, 1400), this.deserializeMenu.scaleImageAnimation(cameraIconView, true, 0.7, 1.4));
		Button button= new Button();
		button.setGraphic(cameraIconView);
		button.setTextAlignment(TextAlignment.LEFT);
		button.setText("Game ID " + g.getGameID());
		button.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
		switch(i%4){
		case 1:
			button.setStyle("-fx-background-color: #900DFF;"
					+  "-fx-background-radius: 50; "+  
		            "  -fx-border-radius: 50;" +
		            "-fx-text-fill: white");
			break;
		case 2:
			button.setStyle("-fx-background-color: #32DBF0; "
					+  "-fx-background-radius: 50; "+  
		            "  -fx-border-radius: 50;" +
		            "-fx-text-fill: white");
			break;
		case 3:
			button.setStyle("-fx-background-color: #FAE100; "+
					 "-fx-background-radius: 50; "+  
			            "  -fx-border-radius: 50;" +
			            "-fx-text-fill: white");
			break;
		case 4:
			button.setStyle("-fx-background-color: #FF0181; "
					+  "-fx-background-radius: 50; "+  
		            "  -fx-border-radius: 50;" +
		            "-fx-text-fill: white");
			break;
		default:
			button.setStyle("-fx-background-color: #FF0181; "+
					 "-fx-background-radius: 50; "+  
			            "  -fx-border-radius: 50;" +
			            "-fx-text-fill: white");
			break;
		}
		button.setPrefHeight(80);
		button.setPrefWidth(200);
		EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() { 
			@Override
            public void handle(MouseEvent e) 
            { 
				try {
					Game deserializeGame= g.deserialize();
					deserializeGame.getMainBall().initialiseTimer();
					deserializeGame.setBackground(deserializeMenu);
				} catch(Exception f){
					f.printStackTrace();
				}
            } 
        }; 
        this.deserializeMenu.shadowEffect(button);
		button.setOnMouseClicked(event);
		return button;
		
	}
	
	public Button exit() {
		Image cameraIcon = new Image("exit.png");
		ImageView cameraIconView = new ImageView(cameraIcon);
		cameraIconView.setFitHeight(30);
		cameraIconView.setFitWidth(30);
		Button button= new Button();
		button.setGraphic(cameraIconView);
		button.setPrefHeight(50);
		button.setPrefWidth(50);
		button.setStyle(
	            "-fx-background-color: #707070; " +
	            "-fx-background-radius: 50; "+  
	            "  -fx-border-radius: 50;"
	    );
		EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() { 
			@Override
            public void handle(MouseEvent e) 
            { 
				try {
					deserializeMenu.serialize();
				} catch(Exception ex){
					ex.printStackTrace();
				}
               window.close();
            } 
        }; 
        this.deserializeMenu.shadowEffect(button);
		button.setOnMouseClicked(event);
		return button;
	}
	
	public void shadowEffect(Button button) {
		 DropShadow shadow = new DropShadow();
		 shadow.setRadius(5.0);
		 shadow.setOffsetX(5.0);
		 shadow.setOffsetY(5.0);
	     button.addEventHandler(MouseEvent.MOUSE_ENTERED,
	                new EventHandler<MouseEvent>() {
	                  @Override
	                  public void handle(MouseEvent e) {
	                    button.setEffect(shadow);
	                  }
	    });
	    button.addEventHandler(MouseEvent.MOUSE_EXITED,
	                new EventHandler<MouseEvent>() {
	                  @Override
	                  public void handle(MouseEvent e) {
	                    button.setEffect(null);
	                  }
	    });
	}
}

class Game implements Serializable {
	private static long serialVersionUID=20L;
	private MainPage mainMenu;
	private int GameID;
	private int CurrentScore;
	private int lives;
	private transient TextFlow ScoreTemplate;
	private transient TextFlow LivesTemplate;
	private Ball MainBall;
	private ArrayList<colorBlock> colorBox;
	private ArrayList<Obstacle> obstacles;
	private ArrayList<Star> Stars;
	private Obstacle CurrentObstacle;
	private int counter;
	private transient ImageView pointer;
	private double pointerX;
	private double pointerY;
	private double HeadingY;
	private transient Group head;
	
	Game(int id, MainPage menu){
		this.GameID=id;
		this.CurrentScore=0;
		this.lives=0;
		this.MainBall= new Ball(this);
		this.colorBox=new ArrayList<colorBlock>();
		this.colorBox.add(new colorBlock(60));
		this.counter=0;
		this.pointerX=183;
		this.pointerY=525;
		this.obstacles = new ArrayList<Obstacle>();
		this.Stars = new ArrayList<Star>();
		this.Stars.add(new Star(250));
		this.obstacles.add(new BallRectangle());
		this.setBackground(menu);
	}
	
	public void serialize() throws FileNotFoundException,IOException{
		ObjectOutputStream out=null;
		try {
			out=new ObjectOutputStream(new FileOutputStream(this.GameID+".txt"));
			out.writeObject(this);
		}finally{
			out.close();
		}
	}
	
	public Game deserialize() throws FileNotFoundException, IOException, ClassNotFoundException{
		ObjectInputStream in=null;
		Game play=null;
		try {
			in=new ObjectInputStream(new FileInputStream(this.GameID+".txt"));
			play=(Game) in.readObject();
		} finally {
			in.close();
		}
		return play;
	}
	
	public double getHeadingY() {
		return this.HeadingY;
	}
	
	public double getPointerY() {
		return this.pointerY;
	}
	
	public void setHeadingY(double d) {
		this.HeadingY=d;
	}
	
	public void setPointerY(double d) {
		this.pointerY=d;
	}
	
	public MainPage getMainMenu() {
		return this.mainMenu;
	}
	
	public ArrayList<Obstacle> getObstacles(){
		return this.obstacles;
	}
	
	public void setCurrentObstacle(Obstacle o) {
		this.CurrentObstacle= o;
	}
	
	public void setCounter(int s) {
		this.counter=s;
	}
	
	public int getCounter() {
		return this.counter;
	}
	
	public Ball getMainBall() {
		return this.MainBall;
	}
	
	public int getGameID() {
		return this.GameID;
	}
	
	public void setBackground(MainPage menu) {
		this.mainMenu=menu;
		ImageView imageView = this.setGameName(600);
		this.head=new Group(imageView);
		this.head.setLayoutY(this.HeadingY);
		this.mainMenu.setMainHeading(this.head, 600);
		Group root = new Group(); 
		Button pauseMenu= this.PauseGame();
		pauseMenu.setLayoutX(20);
	    pauseMenu.setLayoutY(20);
	    Button saveMenu= this.SaveGameOption();
	    saveMenu.setLayoutX(20);
	    saveMenu.setLayoutY(90);
	    this.MainBall.createBall();
		Circle ball= this.MainBall.getBall();
		this.CurrentObstacle= this.obstacles.get(this.counter);
		this.pointer =this.addPointer();
		root.getChildren().add(this.pointer); 
		
		for(colorBlock c: this.colorBox) {
			c.createColorBlock();
			ImageView block = c.getColorBlock();
			root.getChildren().addAll(block);
		}
		
		Iterator itr = this.obstacles.iterator();     //Iterator design pattern
		while(itr.hasNext()) {
			Obstacle o = (Obstacle) itr.next();
			o.createObstacle(this.MainBall);
			this.MainBall.addChangeListener(o);
			Group obstacleLook = o.getObstacle();
			if(o.getClass().getName().equals("Rhombus")||o.getClass().getName().equals("Cross")) {
				this.mainMenu.rotateGroup(obstacleLook, false, 4000);
			}
			else if(o.getClass().getName().equals("SimpleRing")) {
				this.scaleImageAnimation(o.getObstacle(), true, 1.6, 1.3);
			}
			root.getChildren().addAll(obstacleLook);
		}
		Iterator st=this.Stars.iterator();       //Iterator design pattern
		while(st.hasNext()) {
			Star s=(Star)st.next();
			s.createStar();
			ImageView StarLook = s.getStar();
			if(s.getClass().getName().equals("SpecialStar")) {
				this.mainMenu.scaleImageAnimation(StarLook, true, 1, 2);
			}
			else this.mainMenu.scaleImageAnimation(StarLook, true, 1, 1.2);
			root.getChildren().addAll(StarLook);
		}
		root.getChildren().addAll(this.head);
		root.getChildren().addAll(this.displayScore(), this.displayLives());
		root.getChildren().addAll(pauseMenu,saveMenu);
		root.getChildren().add(ball);
		Scene scene = new Scene(root,this.mainMenu.getWindowWidth(),this.mainMenu.getWindowHeight());
		EventHandler<KeyEvent> keyEventHandler = new EventHandler<KeyEvent>() {
		      @Override 
		      public void handle(KeyEvent event) {
		    	  if(event.getCode()==KeyCode.UP) {
		    		  MainBall.moveBall(pointer, root);
		    	  }
		      }
		 };
		scene.setFill(Color.web("#282828"));
		scene.setOnKeyReleased(keyEventHandler);
		this.mainMenu.getWindow().setScene(scene);
	}

	public Obstacle getCurrentObstacle() {
		return this.CurrentObstacle;
	}
	
	public ImageView addPointer() {
		Image image = new Image("pointer.png");
		ImageView imageView = new ImageView(image);
		imageView.setX(this.pointerX); 
	    imageView.setY(this.pointerY); 
	    imageView.setFitHeight(50); 
	    imageView.setFitWidth(50); 
	    imageView.setPreserveRatio(true);
	    return imageView;
	}
	
	public TextFlow displayScore() {
		this.ScoreTemplate= new TextFlow();
		this.ScoreTemplate.setLayoutX(250);
		this.ScoreTemplate.setLayoutY(20);
		this.ScoreTemplate.setPrefWidth(150);
		Text text_1 = new Text("SCORE: " + this.CurrentScore);
		text_1.setFill(Color.WHITE);
		text_1.setFont(Font.font("Verdana", FontWeight.BOLD, 22));
		this.ScoreTemplate.setTextAlignment(TextAlignment.CENTER);
		this.ScoreTemplate.getChildren().add(text_1);
	    return this.ScoreTemplate;
	}
	
	public TextFlow displayLives() {
		this.LivesTemplate= new TextFlow();
		this.LivesTemplate.setLayoutX(20);
		this.LivesTemplate.setLayoutY(620);
		Text text_1 = new Text(this.lives+"");
		text_1.setFill(Color.WHITE);
		text_1.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
		this.LivesTemplate.setTextAlignment(TextAlignment.CENTER);
		this.LivesTemplate.getChildren().add(text_1);
	    return this.LivesTemplate;
	}
	
	public Button PauseGame() {
		Image pauseIcon = new Image("pause.png");
		ImageView pauseIconView = new ImageView(pauseIcon);
		pauseIconView.setFitHeight(30);
		pauseIconView.setFitWidth(30);
		Button button= new Button();
		button.setGraphic(pauseIconView);
		button.setPrefHeight(50);
		button.setPrefWidth(50);
		button.setStyle(
	            "-fx-background-color: #707070; " +
	            "-fx-background-radius: 50; "+  
	            "  -fx-border-radius: 50;"
	    );
		EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() { 
			@Override
            public void handle(MouseEvent e) 
            { 
				MainBall.getTimer().pause();
				displayPauseOptions();
            } 
        }; 
        this.mainMenu.shadowEffect(button);
		button.setOnMouseClicked(event);
		return button;
	}
	
	public void displayPauseOptions() {
		ImageView imageView1= this.addCross();
		imageView1.setX(0); 
	    imageView1.setY(80); 
	    ImageView imageView2= this.addCross();
		imageView2.setX(300); 
	    imageView2.setY(80); 
		TextFlow text_flow = new TextFlow();
		text_flow.setLayoutX(45);
		text_flow.setLayoutY(120);
		text_flow.setPrefWidth(300);
		Text text_1 = new Text("GAME PAUSED");
		text_1.setFill(Color.WHITE);
		text_1.setFont(Font.font("Verdana", FontWeight.BOLD, 22));
		text_flow.setTextAlignment(TextAlignment.CENTER);
	    text_flow.getChildren().add(text_1);
	    Group root = new Group(imageView1); 
	    Button resume=this.ResumeButton(root);
	    this.mainMenu.scaleButtonAnimation(resume, true, 1, 1.2);
	    Button exit = this.mainMenu.BackToMainMenu();
	    exit.setLayoutX(180);
        exit.setLayoutY(500);
	    root.getChildren().add(resume);
	    resume.setLayoutX(100);
	    resume.setLayoutY(250);
	    ImageView imageView3 = this.setGameName(600);
	    root.getChildren().addAll(text_flow, imageView2, exit, imageView3);
	    this.mainMenu.setMainHeading(root, 600);
	    Scene scene = new Scene(root,this.mainMenu.getWindowWidth(),this.mainMenu.getWindowHeight());
	    scene.setFill(Color.web("#282828"));
	    this.mainMenu.getWindow().setScene(scene);
	}
	
	public ImageView setGameName(double Y) {
		Image image = new Image("color.png");
		ImageView imageView = new ImageView(image);
		imageView.setX(130); 
	    imageView.setY(Y); 
	    imageView.setFitHeight(100); 
	    imageView.setFitWidth(150); 
	    imageView.setPreserveRatio(true);
	    return imageView;
	}
	
	public Button ResumeButton(Group root) {
		Image Icon = new Image("resume.png");
		ImageView IconView = new ImageView(Icon);
		IconView.setFitHeight(70);
		IconView.setFitWidth(70);
		Button button=new Button();
		button.setGraphic(IconView);
		button.setPrefHeight(80);
		button.setPrefWidth(200);
		button.setStyle(
	            "-fx-background-color: #606060; " +
	            "-fx-background-radius: 10; "+  
	            "  -fx-border-radius: 10;"+
	            "-fx-text-fill: white"
	    );
		EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() { 
			@Override
            public void handle(MouseEvent f) 
            { 
			   Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), e ->  MainBall.getTimer().play()));
			   timeline.play();
               setBackground(mainMenu);
            } 
        }; 
        this.mainMenu.shadowEffect(button);
		button.setOnMouseClicked(event);
		return button;
	}
	
	public ImageView addCross() {
		Image image = new Image("cross.png");
		ImageView imageView = new ImageView(image);
	    imageView.setFitHeight(100); 
	    imageView.setFitWidth(100); 
	    imageView.setPreserveRatio(true); 
	    RotateTransition rotateTransition = this.mainMenu.rotateRing(imageView, false, 1000);
	    return imageView;
	}
	
	public Button SaveGameOption() {
		Image saveIcon = new Image("save.png");
		ImageView saveIconView = new ImageView(saveIcon);
		saveIconView.setFitHeight(40);
		saveIconView.setFitWidth(35);
		Button button= new Button();
		button.setGraphic(saveIconView);
		button.setPrefHeight(50);
		button.setPrefWidth(50);
		button.setStyle(
	            "-fx-background-color: #707070; " +
	            "-fx-background-radius: 50; "+  
	            "  -fx-border-radius: 50;"
	    );
		EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() { 
			@Override
            public void handle(MouseEvent e) 
            { 
				SaveCurrentGame();
            } 
        }; 
        this.mainMenu.shadowEffect(button);
		button.setOnMouseClicked(event);
		return button;
	}
	
	public void SaveCurrentGame() {
		try {
			MainBall.getTimer().pause();
			this.serialize();
		} catch(Exception e){
			e.printStackTrace();
		}
		int indicator=0;
		
		for(Game g: this.mainMenu.getSavedGames()) {
			if(g.GameID==this.GameID) indicator=1;
		}
		if(indicator==0) mainMenu.addToSavedGames(this);
		Image image = new Image("color-switch-icon.png");
		ImageView imageView = new ImageView(image);
		imageView.setX(50); 
	    imageView.setY(150); 
	    imageView.setFitHeight(300); 
	    imageView.setFitWidth(550); 
	    imageView.setPreserveRatio(true); 
	    ParallelTransition parallel= new ParallelTransition(imageView,this.mainMenu.rotateRing(imageView, false, 1000), this.mainMenu.scaleImageAnimation(imageView, true, 1, 0.7));
	    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2000), e -> parallel.stop()));
		timeline.play();
		Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(2000), e -> this.mainMenu.displayMenuOptions()));
		timeline2.play();
		TextFlow text_flow = new TextFlow();
		text_flow.setLayoutX(50);
		text_flow.setLayoutY(500);
		text_flow.setPrefWidth(300);
		Text text_1 = new Text("Saving Game...");
		text_1.setFill(Color.WHITE);
		text_1.setFont(Font.font("Verdana", FontWeight.BOLD, 22));
		text_flow.setTextAlignment(TextAlignment.CENTER);
		text_flow.setEffect(new BoxBlur(1, 1, 1));
	    text_flow.getChildren().add(text_1);
	    Group root = new Group(imageView); 
	    root.getChildren().add(text_flow);
	    Scene scene = new Scene(root,this.mainMenu.getWindowWidth(),this.mainMenu.getWindowHeight());
	    scene.setFill(Color.web("#272727"));
	    this.mainMenu.getWindow().setScene(scene);
	}
	
	public void setLostScene() {
		Group root = new Group();   
		Button button1 = this.createContinueGameButton();
		Button button2 = this.createNewGameButton();
		Button button3 = this.createExitButton();
		ImageView imageView1= this.addHeading();
		imageView1.setX(80); 
	    imageView1.setY(100); 

		ImageView imageView3 = this.setGameName2(root);
		this.displayEndScore(root);
		this.displayHighestScore(root);
		ImageView star= this.createStar(40,175, 490);
		ImageView star1=this.createStar(20,160, 510);
		ImageView star2=this.createStar(20,213, 510);
		this.mainMenu.scaleImageAnimation(star, true, 1, 0.5);
		this.mainMenu.scaleImageAnimation(star1, true, 1, 1.5);
		this.mainMenu.scaleImageAnimation(star2, true, 1, 1.5);
		Text t= new Text(""+this.mainMenu.getTotalStarsCollected());
		t.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
		t.setFill(Color.WHITE);
		TextFlow text=new TextFlow(t);
		text.setLayoutX(148);
		text.setLayoutY(530);
		text.setPrefWidth(100);
		text.setTextAlignment(TextAlignment.CENTER);
		root.getChildren().addAll(imageView1,imageView3 , button1, button2 , button3, star, star1, star2, text); 
		this.setMainHeading2(root, 600);
	    Scene scene = new Scene(root,this.mainMenu.getWindowWidth(),this.mainMenu.getWindowHeight());
	    scene.setFill(Color.web("#282828"));
	    this.mainMenu.getWindow().setScene(scene);
	  
	}
	
	public Button createContinueGameButton() {
		Button button1 = new Button("Use Stars to Continue");
		button1.setLayoutX(80);
		button1.setLayoutY(210);
		button1.setStyle("-fx-background-color: #900DFF;"
				+  "-fx-background-radius: 50; "+  
	            "  -fx-border-radius: 50;" +
	            "-fx-text-fill: white;" +
	            "-fx-pref-height: 70px; " + 
	            "    -fx-pref-width: 250px; ");
		button1.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
		this.mainMenu.shadowEffect(button1);
		EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() { 
			@Override
            public void handle(MouseEvent e) 
            { 
					try {
						if(mainMenu.getTotalStarsCollected()<10) {
							throw new InsufficientPointsException("Not enough points to continue!!");
						}
						else {
							mainMenu.setTotalStarsCollected(mainMenu.getTotalStarsCollected()-10);
							MainBall.setHasLost(0);
							setAfterLostPosition();
							setBackground(mainMenu);
						}
					}
					catch(InsufficientPointsException f) {
						showMessageBox();
					}
            }
        }; 
		button1.setOnMouseClicked(event);
		return button1;
	}
	
	public void setAfterLostPosition() {
		for(int i=obstacles.size()-1;i>=0;i--) {
			if(obstacles.get(i).getY()>0 && obstacles.get(i).getY()<200) {
				if(obstacles.get(i).getClass().getName().equals("Rhombus")||obstacles.get(i).getClass().getName().equals("SimpleRing")) {
					MainBall.setY(obstacles.get(i).getY()+400);
					MainBall.getBall().setCenterY(obstacles.get(i).getY()+400);
					break;
				}
				else if(obstacles.get(i).getClass().getName().equals("BallRectangle")) {
					MainBall.setY(obstacles.get(i).getY()+300);
					MainBall.getBall().setCenterY(obstacles.get(i).getY()+300);
					break;
				}
				else {
					MainBall.setY(obstacles.get(i).getY()+200);
					MainBall.getBall().setCenterY(obstacles.get(i).getY()+200);
					break;
				}
			}
			else if(obstacles.get(i).getY()>=200) {
				MainBall.setY(obstacles.get(i).getY()-100);
				MainBall.getBall().setCenterY(obstacles.get(i).getY()-100);
				break;
			}
		}
	}
	
	public void showMessageBox() {
		Alert a = new Alert(AlertType.NONE); 
		a.setAlertType(AlertType.INFORMATION); 
		ImageView star=new ImageView(new Image("star2.png"));
		star.prefHeight(70);
		star.prefWidth(70);
		mainMenu.scaleImageAnimation(star, true, 1, 0.5);
		a.getDialogPane().setStyle("-fx-background-color: #282828;");
		Stage s=(Stage)a.getDialogPane().getScene().getWindow();
		s.getIcons().add(new Image("color-switch-icon.png"));
		a.setGraphic(star);
		a.setHeaderText(null);
	    a.setTitle("OOPS SORRY!!");
	    String content = "Insufficient stars available. You require atleast 10 stars to continue with the game.";
	    int length = content.length();
	    Text text = new Text(10, 20, "");
	    Animation animation = new Transition() {
	    	{
	            setCycleDuration(Duration.millis(3500));
	        }
	    
	        protected void interpolate(double f) {
	            int n = Math.round(length * (float) f);
	            text.setText(content.substring(0, n));
	        }
	    
	    };
	    animation.play();
		text.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
		text.setFill(Color.WHITE);
		TextFlow textFlow=new TextFlow(text);
	    a.getDialogPane().setContent(textFlow);
	    a.getDialogPane().setPrefWidth(400);
	    a.getDialogPane().setPrefHeight(125);
		a.show();
	}
	
	public Button createNewGameButton() {
		Button button2 = new Button("Start New game");
		button2.setLayoutX(80);
		button2.setLayoutY(310);
		button2.setStyle("-fx-background-color: #32DBF0; "
				+  "-fx-background-radius: 50; "+  
	            "  -fx-border-radius: 50;" +
	            "-fx-text-fill: white; " +
	            "-fx-pref-height: 70px; " + 
	            "    -fx-pref-width: 250px; ");
		button2.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
		this.mainMenu.shadowEffect(button2);
		EventHandler<MouseEvent> event2 = new EventHandler<MouseEvent>() { 
			@Override
            public void handle(MouseEvent e) 
            { 
               mainMenu.New_Game();
            } 
        }; 
		button2.setOnMouseClicked(event2);
		return button2;
	}
	
	public Button createExitButton() {
		Button button3 = new Button("Exit the Game");
		button3.setLayoutX(80);
		button3.setLayoutY(410);
		button3.setStyle("-fx-background-color: #FF0181; "
				+  "-fx-background-radius: 70; "+  
	            "  -fx-border-radius: 70;" +
	            "-fx-text-fill: white; " +
	            "-fx-pref-height: 70px; " + 
	            "    -fx-pref-width: 250px; ");

		button3.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
		this.mainMenu.shadowEffect(button3);
		EventHandler<MouseEvent> event3 = new EventHandler<MouseEvent>() { 
			@Override
            public void handle(MouseEvent e) 
            { 
               mainMenu.displayMenuOptions();
            } 
        }; 
		button3.setOnMouseClicked(event3);
		return button3;
	}
	
	public ImageView createStar(int size, int x, int y) {
		Image image = new Image("star2.png");
		ImageView starView = new ImageView(image);
		starView.setX(x); 
		starView.setY(y); 
		starView.setFitHeight(size); 
		starView.setFitWidth(size+10);
		starView.setPreserveRatio(true);
		return starView;
	}
	
	public ImageView createStar2(int size, int x, int y) {
		Image image = new Image("star.png");
		ImageView starView = new ImageView(image);
		starView.setX(x); 
		starView.setY(y); 
		starView.setFitHeight(size); 
		starView.setFitWidth(size+10);
		starView.setPreserveRatio(true);
		return starView;
	}
	
	public ImageView addHeading() {
		Image image = new Image("lostGame.png");
		ImageView imageView = new ImageView(image);
	    imageView.setFitHeight(600); 
	    imageView.setFitWidth(250); 
	    imageView.setPreserveRatio(true);
	    Glow glow = new Glow(); 
	    glow.setLevel(0.8);  
	    imageView.setEffect(glow);      
	    FadeTransition rotateTransition = this.fadeRing(imageView, false);
	    return imageView;
	}
	
	public TextFlow addHeading2() {
		TextFlow text_flow = new TextFlow();
		text_flow.setLayoutX(50);
		text_flow.setLayoutY(300);
		text_flow.setPrefWidth(300);
		Text text_1 = new Text("CONTINUE...");
		text_1.setFill(Color.WHITE);
		text_1.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
		text_flow.setTextAlignment(TextAlignment.CENTER);
	    text_flow.getChildren().add(text_1);
	    Glow glow = new Glow(); 
	    glow.setLevel(0.8);  
	    text_flow.setEffect(glow);      
	    FadeTransition rotateTransition = this.fadeRing2(text_flow, false);
	    return text_flow;
	}
	
	public FadeTransition fadeRing2(TextFlow image, boolean flag) {
		FadeTransition fade = new FadeTransition();  
        fade.setDuration(Duration.millis(2000));   
        fade.setFromValue(10);  
        fade.setToValue(0.1);     
        fade.setCycleCount(1000);  
        fade.setAutoReverse(true); 
        fade.setNode(image);   
        fade.play(); 
        return fade;
	}
	
	public FadeTransition fadeRing(ImageView image, boolean flag) {
		FadeTransition fade = new FadeTransition();  
        fade.setDuration(Duration.millis(2000));   
        fade.setFromValue(10);  
        fade.setToValue(0.1);     
        fade.setCycleCount(1000);  
        fade.setAutoReverse(true); 
        fade.setNode(image);   
        fade.play(); 
        return fade;
	}
	
	public ImageView setGameName2(Group root) {
		Image image = new Image("color.png");
		ImageView imageView = new ImageView(image);
		imageView.setX(125); 
	    imageView.setY(600); 
	    imageView.setFitHeight(100); 
	    imageView.setFitWidth(150); 
	    imageView.setPreserveRatio(true);
	    return imageView;
	}
	
	public void setMainHeading2(Group root, int y) {
		Image image = new Image("ring.png");
		ImageView imageView = new ImageView(image);
		ImageView imageView2 = new ImageView(image);
		imageView.setX(163); 
	    imageView.setY(y); 
	    imageView.setFitHeight(63); 
	    imageView.setFitWidth(30); 
	    imageView2.setX(212); 
	    imageView2.setY(y); 
	    imageView2.setFitHeight(63); 
	    imageView2.setFitWidth(30); 
	    imageView2.setPreserveRatio(true);
	    imageView.setPreserveRatio(true);
	    this.mainMenu.rotateRing(imageView, false, 1000);
	    this.mainMenu.rotateRing(imageView2, false, 1000);
	    root.getChildren().add(imageView);
	    root.getChildren().add(imageView2);
	}
	
	
	public void displayEndScore(Group root) {
		TextFlow text_flow = new TextFlow();
		text_flow.setLayoutX(310);
		text_flow.setLayoutY(20);
		text_flow.setPrefWidth(70);
		Text text_1 = new Text("SCORE " + this.CurrentScore);
		text_1.setFill(Color.WHITE);
		text_1.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
		text_flow.setTextAlignment(TextAlignment.CENTER);
	    text_flow.getChildren().add(text_1);
	    root.getChildren().addAll(text_flow);
	}
	
	public void displayHighestScore(Group root) {
		TextFlow text_flow = new TextFlow();
		text_flow.setLayoutX(20);
		text_flow.setLayoutY(20);
		text_flow.setPrefWidth(150);
		this.setHighestScore();
		Text text_1 = new Text(" HIGHEST SCORE " + this.mainMenu.getHighestScore());
		text_1.setFill(Color.WHITE);
		text_1.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
		text_flow.setTextAlignment(TextAlignment.CENTER);
	    text_flow.getChildren().add(text_1);
	    root.getChildren().addAll(text_flow);
	}
	
	public void setHighestScore() {
		if(this.CurrentScore>this.mainMenu.getHighestScore()) {
			this.mainMenu.setHighestScore(this.CurrentScore);
		}
	}
	
	public void setLiveScene() {
		Group root = new Group();   
		TextFlow imageView1= this.addHeading2();
		ImageView imageView3 = this.setGameName2(root);
		ImageView star= this.createStar2(40,175, 470);
		ImageView star1=this.createStar2(20,160, 490);
		ImageView star2=this.createStar2(20,213, 490);
		this.mainMenu.scaleImageAnimation(star, true, 1, 0.5);
		this.mainMenu.scaleImageAnimation(star1, true, 1, 1.5);
		this.mainMenu.scaleImageAnimation(star2, true, 1, 1.5);
		Text t= new Text(""+this.lives);
		t.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
		t.setFill(Color.WHITE);
		TextFlow text=new TextFlow(t);
		text.setLayoutX(148);
		text.setLayoutY(510);
		text.setPrefWidth(100);
		text.setTextAlignment(TextAlignment.CENTER);
		root.getChildren().addAll(imageView1,imageView3 , star, star1, star2, text); 
		this.setMainHeading2(root, 600);
	    Scene scene = new Scene(root,this.mainMenu.getWindowWidth(),this.mainMenu.getWindowHeight());
	    scene.setFill(Color.web("#282828"));
	    this.mainMenu.getWindow().setScene(scene);
	}
	
	public void moveScene(ImageView image) {
		for(Obstacle o: this.obstacles) {
			o.getObstacle().setLayoutY(o.getY()+3);
			o.setY(o.getY()+3);
		}
		for(Star s: this.Stars) {
			s.getStar().setY(s.getY()+3);
			s.setY(s.getY()+3);
		}
		for(colorBlock c: this.colorBox) {
			c.getColorBlock().setY(c.getY()+3);
			c.setY(c.getY()+3);
		}
		image.setY(image.getY()+3);
		this.setPointerY(this.pointerY+3);
		this.setHeadingY(this.HeadingY+3);
		this.head.setLayoutY(this.head.getLayoutY()+3);
	}
	
	public void cleanList(Group r) {
		ArrayList<Obstacle> temp= new ArrayList<Obstacle>();
		for(Obstacle o:this.obstacles) {
			if(o.getY()>this.mainMenu.getWindowHeight()) {
				temp.add(o);
				this.setCounter(this.counter-1);
				r.getChildren().remove(o.getObstacle());
			}
		}
		for(Obstacle ob: temp) {
			this.obstacles.remove(ob);
			ob=null;
		}
		temp=null;
	}
	
	public void selectBallRectangle(Group r) {
		Obstacle newObstacle=new BallRectangle(100,this.CurrentObstacle.getY()-500);
		newObstacle.createObstacle(this.MainBall);
		this.MainBall.addChangeListener(newObstacle);
		this.obstacles.add(newObstacle);
		this.setCounter(this.counter+1);
		this.setCurrentObstacle(this.obstacles.get(this.obstacles.size()-1));
		r.getChildren().add(0, this.CurrentObstacle.getObstacle());
		int rand=this.displayStar();
		if(rand==1) {
			Star newStar= new Star(this.CurrentObstacle.getY()+75);
			newStar.createStar();
			this.Stars.add(newStar);
			this.mainMenu.scaleImageAnimation(newStar.getStar(), true, 1, 1.2);
			r.getChildren().add(1,newStar.getStar());
		}
		else if(rand==2) {
			colorBlock newBlock= new colorBlock(this.CurrentObstacle.getY()-105);
			newBlock.createColorBlock();
			this.colorBox.add(newBlock);
			r.getChildren().add(1, newBlock.getColorBlock());
		}
		else if(rand==3) {
			Star newStar= new SpecialStar(this.CurrentObstacle.getY()+75);
			newStar.createStar();
			this.Stars.add(newStar);
			this.mainMenu.scaleImageAnimation(newStar.getStar(), true, 1, 2);
			r.getChildren().add(1,newStar.getStar());
		}
	}
	
	public void selectRhombus(Group r) {
		Obstacle newObstacle=new Rhombus(-10,this.CurrentObstacle.getY()-600);
		newObstacle.createObstacle(this.MainBall);
		this.MainBall.addChangeListener(newObstacle);
		this.obstacles.add(newObstacle);
		this.mainMenu.rotateGroup(newObstacle.getObstacle(), false, 4000);
		this.setCounter(this.counter+1);
		this.setCurrentObstacle(this.obstacles.get(this.obstacles.size()-1));
		r.getChildren().add(0, this.CurrentObstacle.getObstacle());
		int rand=this.displayStar();
		if(rand==1) {
			Star newStar= new Star(this.CurrentObstacle.getY()+155);
			newStar.createStar();
			this.Stars.add(newStar);
			this.mainMenu.scaleImageAnimation(newStar.getStar(), true, 1, 1.2);
			r.getChildren().add(1,newStar.getStar());
		}
		else if(rand==2) {
			colorBlock newBlock= new colorBlock(this.CurrentObstacle.getY()-55);
			newBlock.createColorBlock();
			this.colorBox.add(newBlock);
			r.getChildren().add(1, newBlock.getColorBlock());
		}
		else if(rand==3) {
			Star newStar= new SpecialStar(this.CurrentObstacle.getY()+155);
			newStar.createStar();
			this.Stars.add(newStar);
			this.mainMenu.scaleImageAnimation(newStar.getStar(), true, 1, 2);
			r.getChildren().add(1,newStar.getStar());
		}
	}
	
	public void selectSimpleRing(Group r) {
		Obstacle newObstacle=new SimpleRing(100,this.CurrentObstacle.getY()-500);
		newObstacle.createObstacle(this.MainBall);
		this.MainBall.addChangeListener(newObstacle);
		this.obstacles.add(newObstacle);
		this.scaleImageAnimation(newObstacle.getObstacle(), true, 1.6, 1.3);
		this.setCounter(this.counter+1);
		this.setCurrentObstacle(this.obstacles.get(this.obstacles.size()-1));
		r.getChildren().add(0, this.CurrentObstacle.getObstacle());
		int rand=this.displayStar();
		if(rand==1) {
			Star newStar= new Star(this.CurrentObstacle.getY()+75);
			newStar.createStar();
			this.Stars.add(newStar);
			this.mainMenu.scaleImageAnimation(newStar.getStar(), true, 1, 1.2);
			r.getChildren().add(1,newStar.getStar());
		}
		else if(rand==2) {
			colorBlock newBlock= new colorBlock(this.CurrentObstacle.getY()-105);
			newBlock.createColorBlock();
			this.colorBox.add(newBlock);
			r.getChildren().add(1, newBlock.getColorBlock());
		}
		else if(rand==3) {
			Star newStar= new SpecialStar(this.CurrentObstacle.getY()+75);
			newStar.createStar();
			this.Stars.add(newStar);
			this.mainMenu.scaleImageAnimation(newStar.getStar(), true, 1, 2);
			r.getChildren().add(1,newStar.getStar());
		}
	}
	
	public void selectDoubleCross(Group r) {
		Obstacle newObstacle=this.selectCross();
		newObstacle.createObstacle(this.MainBall);
		this.MainBall.addChangeListener(newObstacle);
		this.obstacles.add(newObstacle);
		this.mainMenu.rotateGroup(newObstacle.getObstacle(), false, 4000);
		this.setCounter(this.counter+1);
		this.setCurrentObstacle(this.obstacles.get(this.obstacles.size()-1));
		r.getChildren().add(0, this.CurrentObstacle.getObstacle());
		int rand=this.displayStar();
		if(rand==1) {
			Star newStar= new Star(this.CurrentObstacle.getY()-65);
			newStar.createStar();
			this.Stars.add(newStar);
			this.mainMenu.scaleImageAnimation(newStar.getStar(), true, 1, 1.2);
			r.getChildren().add(1,newStar.getStar());
		}
		else if(rand==2) {
			colorBlock newBlock= new colorBlock(this.CurrentObstacle.getY()-75);
			newBlock.createColorBlock();
			this.colorBox.add(newBlock);
			r.getChildren().add(1, newBlock.getColorBlock());
		}
		else if(rand==3) {
			Star newStar= new SpecialStar(this.CurrentObstacle.getY()-65);
			newStar.createStar();
			this.Stars.add(newStar);
			this.mainMenu.scaleImageAnimation(newStar.getStar(), true, 1, 2);
			r.getChildren().add(1,newStar.getStar());
		}
	}
	
	public void selectObstacle(Group r) {  //factory design pattern
		this.cleanList(r);
		Random rnum = new Random();
		int o=rnum.nextInt(4);
		if(o==0) {
			this.selectBallRectangle(r);
		}
		else if(o==1) {
			this.selectRhombus(r);
		}
		else if(o==2) {
			this.selectSimpleRing(r);
		}
		else {
			this.selectDoubleCross(r);
		}
	}
	
	public ScaleTransition scaleImageAnimation(Group image, boolean flag, double speed, double x) {
		ScaleTransition scaletransition=new ScaleTransition(Duration.seconds(speed),image);
		scaletransition.setCycleCount(Animation.INDEFINITE); 
		scaletransition.setToX(x);
		scaletransition.setToY(x);
		scaletransition.setAutoReverse(flag); 
		scaletransition.play(); 
		return scaletransition;
	}
	
	public void useLives(Group r) {
		this.lives=this.lives-1;
		r.getChildren().remove(this.LivesTemplate);
		r.getChildren().add(this.displayLives());
	}
	
	public boolean isLivesLeft() {
		if(this.lives>=1) {
			return true;
		}
		return false;
	}
	
	public int displayStar() {
		Random rnum = new Random();
		int o=rnum.nextInt(3);
		if(o==0) {
			return 1;
		}
		else if(o==1) return 2;
		else return 3;
	}
	
	public Obstacle selectCross() {
		Random rnum = new Random();
		int o=rnum.nextInt(2);
		if(o==0) {
			return new Cross(180,this.CurrentObstacle.getY()-400);
		}
		else {
			return new Cross(80,this.CurrentObstacle.getY()-400);
		}
	}
	
	public boolean isStarCollected(Group r) {
		if(this.Stars.size()!=0) {
			if(this.MainBall.getY()<=this.Stars.get(0).getY()+35) {
				if(this.Stars.get(0).getClass().getName().equals("SpecialStar")) {
					this.lives=this.lives+1;
					r.getChildren().remove(this.LivesTemplate);
					r.getChildren().add(this.displayLives());
				}
				r.getChildren().remove(this.Stars.get(0).getStar());
				Star s=this.Stars.remove(0);
				s=null;
				return true;
			}
		}
		return false;
	}
	
	public void updateScore(Group root) {
		this.CurrentScore=this.CurrentScore+5;
		this.mainMenu.setTotalStarsCollected(this.mainMenu.getTotalStarsCollected()+1);
		root.getChildren().remove(this.ScoreTemplate);
		root.getChildren().add(this.displayScore());
	}
	
	public boolean isPassedColorSwitch() {
		if(this.colorBox.size()!=0) {
			if(this.MainBall.getY()<=this.colorBox.get(0).getY()+30) {
				return true;
			}
		}
		return false;
	}
	
	public void switchColor(Group r) {
		if(this.colorBox.size()!=0) {
			this.MainBall.setColor(this.colorBox.get(0).pickRandomColor());
			r.getChildren().remove(this.colorBox.get(0).getColorBlock());
			colorBlock c=this.colorBox.remove(0);
			c=null;
			System.gc();
		}
	}
}

class InsufficientPointsException extends Exception{
	
	InsufficientPointsException(String message){
		super(message);
	}
}

class NoSavedGameException extends Exception{
	
	NoSavedGameException(String message){
		super(message);
	}
}

class Ball implements Serializable{
	private transient Color color;
	private int ColorNum;
	private final int radius;
	private transient Circle BallView;
	private double Y_coordinate;
	private double X_coordinate;
	private final int constantDistanceTravelled;
	private double dy= 4;
	private Game gameplay;
	private int hasLost;
	private transient Timeline timer;
	private int time;
	
	Ball(Game game){
		this.radius=10;
		this.Y_coordinate= 510;
		this.X_coordinate = 200;
		this.time=15;
		this.color= Color.web("#32DBF0");
		this.ColorNum=1;
		this.constantDistanceTravelled=80;
		this.initialiseTimer();
		this.gameplay=game;
	}
	
	public void setHasLost(int i) {
		this.hasLost=i;
	}
	
	public void initialiseTimer() {
		this.timer=new Timeline();
	}
	
	public double getY() {
		return this.Y_coordinate;
	}
	
	public void setY(double d) {
		this.Y_coordinate=d;
	}
	
	public void createBall() {
		this.BallView= new Circle(this.X_coordinate, this.Y_coordinate, this.radius);
		this.getColorFill(this.ColorNum);
		this.BallView.setFill(this.color);
	}
	
	public Circle getBall() {
		return this.BallView;
	}
	
	public Timeline getTimer() {
		return this.timer;
	}
	
	public void getColorFill(int Num) {
		if(Num==0) {
			this.color=Color.web("#900DFF");
		}
		else if(Num==1) {
			this.color=Color.web("#32DBF0");
		}
		else if(Num==2) {
			this.color=Color.web("#FAE100");
		}
		else {
			this.color=Color.web("#FF0181");
		}
	}
	
	public void setColor(int Num) {
		this.ColorNum=Num;
		this.getColorFill(this.ColorNum);
		this.BallView.setFill(this.color);
	}
	
	public void moveBall(ImageView image, Group r) {
		this.timer.stop();
		this.dy= 5;
		final double margin= this.getY()-this.constantDistanceTravelled;
		final double gravity= 0.25;
		this.timer = new Timeline(new KeyFrame(Duration.millis(this.time),  new EventHandler<ActionEvent>() {
				 @Override
				 public void handle(ActionEvent t) {
					 BallView.setCenterY(Y_coordinate - dy);
					 setY(Y_coordinate - dy);
					 dy=dy-gravity;
					 if(gameplay.isStarCollected(r)) gameplay.updateScore(r);
					 if(gameplay.isPassedColorSwitch()) gameplay.switchColor(r);
					 if(getY()<= gameplay.getMainMenu().getWindowHeight()/3) gameplay.moveScene(image);
					 if(getY()<= gameplay.getCurrentObstacle().getY()) {
						gameplay.selectObstacle(r);
					 }
					 if (getY() <= margin + BallView.getRadius() ) dy = -dy;
					 if(getY()>= gameplay.getPointerY()- BallView.getRadius()- 5 ) {
						 dy=0;
						 BallView.setCenterY(gameplay.getPointerY()-15);
						 setY(gameplay.getPointerY()-15);
					 }
					 if(getY()>= gameplay.getMainMenu().getWindowHeight()||isLost()) {
						 timer.stop();
						 if(gameplay.isLivesLeft()) {
							 gameplay.useLives(r);
							 hasLost=0;
							 gameplay.setAfterLostPosition();
							 gameplay.setLiveScene();
							 Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2000), e -> gameplay.setBackground(gameplay.getMainMenu())));
							 timeline.play();
						 }
						 else{
							 gameplay.setLostScene();
						 }
					 }
				 }
		}));
		this.timer.setCycleCount(Timeline.INDEFINITE);
		this.timer.play();
	}
	
	public boolean isLost() {
		if(this.hasLost==1) {
			return true;
		}
		return false;
	}
	
	public void addChangeListener(Obstacle o) {
		if(o.getClass().getName().equals("Rhombus")) {
			Rhombus shape=(Rhombus)o;
			this.BallView.boundsInParentProperty().addListener((ChangeListener<? super Bounds>) new ChangeListener<Bounds>() {
	            @Override
	            public void changed(ObservableValue<? extends Bounds> observable,Bounds oldValue, Bounds newValue) {
	                for (Rectangle r :shape.getShapes()) {
	                    if (((Path)Shape.intersect(BallView, r)).getElements().size() > 0) {
	                    	if(!r.getFill().equals(BallView.getFill())) {
	                    		hasLost=1;
							}
	                    }
	                }
	            }
	        });
		}
		else if(o.getClass().getName().equals("Cross")) {
			Cross shape=(Cross)o;
			this.BallView.boundsInParentProperty().addListener((ChangeListener<? super Bounds>) new ChangeListener<Bounds>() {
	            @Override
	            public void changed(ObservableValue<? extends Bounds> observable,Bounds oldValue, Bounds newValue) {
	                for (Rectangle r :shape.getShapes() ) {
	                    if (((Path)Shape.intersect(BallView, r)).getElements().size() > 0) {
	                    	if(!r.getFill().equals(BallView.getFill())) {
	                    		hasLost=1;
							}
	                    }
	                }
	            }
	        });
		}
		else if(o.getClass().getName().equals("BallRectangle")) {
			BallRectangle shape=(BallRectangle)o;
			this.BallView.boundsInParentProperty().addListener((ChangeListener<? super Bounds>) new ChangeListener<Bounds>() {
	            @Override
	            public void changed(ObservableValue<? extends Bounds> observable,Bounds oldValue, Bounds newValue) {
	                for (ArrayList<Circle> c :shape.getShapes() ) {
	                	for(Circle r: c) {
	                		if (((Path)Shape.intersect(BallView, r)).getElements().size() > 0) {
		                    	if(!r.getFill().equals(BallView.getFill())) {
		                    		hasLost=1;
								}
		                    }
	                	}
	                }
	            }
	        });
		}
		if(o.getClass().getName().equals("SimpleRing")) {
			SimpleRing shape=(SimpleRing)o;
			this.BallView.boundsInParentProperty().addListener((ChangeListener<? super Bounds>) new ChangeListener<Bounds>() {
	            @Override
	            public void changed(ObservableValue<? extends Bounds> observable,Bounds oldValue, Bounds newValue) {
	                for (ArrayList<Circle> c :shape.getShapes() ) {
	                	for(Circle r: c) {
	                		if (((Path)Shape.intersect(BallView, r)).getElements().size() > 0) {
		                    	if(!r.getFill().equals(BallView.getFill())) {
		                    		hasLost=1;
								}
		                    }
	                	}
	                }
	            }
	        });
		}
	}
	
}

class colorBlock implements Serializable{
	private final int radius;
	private transient ImageView BlockView;
	private double Y_coordinate;
	private double X_coordinate;
	
	colorBlock(double Y){
		this.radius=37;
		this.Y_coordinate= Y;
		this.X_coordinate = 180;
	}
	
	public double getY() {
		return this.Y_coordinate;
	}
	
	public void setY(double d) {
		this.Y_coordinate=d;
	}
	
	public void createColorBlock() {
		Image image = new Image("coloBlock.png");
		this.BlockView = new ImageView(image);
		this.BlockView.setX(this.X_coordinate); 
		this.BlockView.setY(this.Y_coordinate); 
		this.BlockView.setFitHeight(this.radius); 
		this.BlockView.setFitWidth(this.radius+10);
		this.BlockView.setPreserveRatio(true);
	}
	
	public ImageView getColorBlock() {
		return this.BlockView;
	}
	
	public int pickRandomColor() {
		Random rnum = new Random();
		int o=rnum.nextInt(4);
		return o;
	}
}

class Star implements Serializable{
	protected final int radius;
	protected transient ImageView starView;
	private double Y_coordinate;
	private double X_coordinate;
	
	Star(double Y){
		this.radius=40;
		this.Y_coordinate= Y;
		this.X_coordinate = 177;
	}
	
	public double getY() {
		return this.Y_coordinate;
	}
	
	public double getX() {
		return this.X_coordinate;
	}
	
	public void setY(double d) {
		this.Y_coordinate=d;
	}
	
	public final void createStar() { //template design pattern
		this.createImage();
		this.setPosition();
	}
	
	public void createImage() {
		Image image = new Image("star2.png");
		this.starView = new ImageView(image);
	}
	
	public void setPosition() {
		this.starView.setX(this.X_coordinate); 
		this.starView.setY(this.Y_coordinate); 
		this.starView.setFitHeight(this.radius); 
		this.starView.setFitWidth(this.radius+10);
		this.starView.setPreserveRatio(true);
	}
	
	public ImageView getStar() {
		return this.starView;
	}
}

class SpecialStar extends Star{

	SpecialStar(double Y) {
		super(Y);
	}
	
	@Override
	public void createImage() {
		Image image = new Image("star.png");
		this.starView = new ImageView(image);
	}
	
}

abstract class Obstacle implements Serializable{
	private final int size;
	private double Y_coordinate;
	private double X_coordinate;
	private transient Group root;
	
	Obstacle(){
		this.size=250;
		this.Y_coordinate= 175;
		this.X_coordinate = 100;
	}
	
	Obstacle(double X, double Y){
		this.size=250;
		this.Y_coordinate= Y;
		this.X_coordinate = X;
	}
	
	public double getY() {
		return this.Y_coordinate;
	}
	
	public double getX() {
		return this.X_coordinate;
	}
	
	public void setY(double d) {
		this.Y_coordinate=d;
	}
	
	public void initialiseRoot() {
		this.root=new Group();
	}
	
	public abstract void createObstacle(Ball mainBall);
	
	public Group getObstacle() {
		return this.root;
	}
	
	public void addGroup(Node o) {
		this.root.getChildren().add(o);
	}
	
	public int getSize() {
		return this.size;
	}
	
}

class SimpleRing extends Obstacle{
	private transient ArrayList<Circle> purple;
	private transient ArrayList<Circle> pink;
	private transient ArrayList<Circle> blue;
	private transient ArrayList<Circle> yellow;
	private transient ArrayList<ArrayList<Circle>> shapes;

	SimpleRing() {
		super();
	}
	
	SimpleRing(double X, double Y) {
		super(X,Y);
	}

	@Override
	public void createObstacle(Ball mainBall) {
		 this.initialiseRoot();
		 this.shapes=new ArrayList<ArrayList<Circle>>();
		 this.pink = new ArrayList<Circle>(); 
		 this.purple = new ArrayList<Circle>(); 
		 this.yellow = new ArrayList<Circle>(); 
		 this.blue = new ArrayList<Circle>(); 
		
		 Circle rect= new Circle();
	     rect.setCenterX(100);;
	     rect.setCenterY(100);;
	     rect.setRadius(100);; 
	     int i=this.BuildBalls(pink, "#FF0181", rect, 0);
	     i=this.BuildBalls(purple, "#900DFF", rect, i);
	     i=this.BuildBalls(yellow, "#FAE100", rect, i);
	     i=this.BuildBalls(blue, "#32DBF0", rect, i); 
	         
	     this.shapes.add(this.blue);
	     this.shapes.add(this.yellow);
	     this.shapes.add(this.pink);
	     this.shapes.add(this.purple);
	     this.getObstacle().setLayoutX(this.getX());
   	     this.getObstacle().setLayoutY(this.getY());
	}
	
	public ArrayList<ArrayList<Circle>> getShapes(){
		return this.shapes;
	}
	
	public int BuildBalls(ArrayList<Circle> list, String color, Circle path, int ind) {
		for(int i=0;i<6;i++) {
			Circle c=new Circle(10);
			c.setFill(Color.web(color));
			list.add(c);
			PathTransition pathTransition = new PathTransition();  
			pathTransition.setDuration(Duration.millis(5000));
			pathTransition.setCycleCount(Animation.INDEFINITE);
			pathTransition.setInterpolator(Interpolator.LINEAR);
			pathTransition.setPath(path);  
			pathTransition.setNode(c); 
		    pathTransition.setAutoReverse(false);  
		    Transition transition = pathTransition;
            transition.jumpTo(Duration.millis(5000).multiply(ind * 1.0 / 24));
            ind=ind+1;
            transition.play();
 
			this.addGroup(c);
		}
		return ind;
	}
}

class BallRectangle extends Obstacle{
	private transient ArrayList<Circle> purple;
	private transient ArrayList<Circle> pink;
	private transient ArrayList<Circle> blue;
	private transient ArrayList<Circle> yellow;
	private transient ArrayList<ArrayList<Circle>> shapes;

	BallRectangle() {
		super();
	}
	
	BallRectangle(double X, double Y) {
		super(X,Y);
	}

	@Override
	public void createObstacle(Ball mainBall) {
		 this.initialiseRoot();
		 this.shapes=new ArrayList<ArrayList<Circle>>();
		 this.pink = new ArrayList<Circle>(); 
		 this.purple = new ArrayList<Circle>(); 
		 this.yellow = new ArrayList<Circle>(); 
		 this.blue = new ArrayList<Circle>(); 
		
		 Rectangle rect= new Rectangle();
	     rect.setX(10);
	     rect.setY(10);
	     rect.setWidth(180); 
	     rect.setHeight(180); 
	     int i=this.BuildBalls(pink, "#FF0181", 10, 10, 0, 1, rect, 0);
	     i=this.BuildBalls(purple, "#900DFF", 190, 10, 1, 0, rect, i);
	     i=this.BuildBalls(yellow, "#FAE100", 40, 190, 0, 1, rect, i);
	     i=this.BuildBalls(blue, "#32DBF0", 10, 40, 1, 0, rect, i); 
	         
	     this.shapes.add(this.blue);
	     this.shapes.add(this.yellow);
	     this.shapes.add(this.pink);
	     this.shapes.add(this.purple);
	     this.getObstacle().setLayoutX(this.getX());
   	     this.getObstacle().setLayoutY(this.getY());
	}
	
	public ArrayList<ArrayList<Circle>> getShapes(){
		return this.shapes;
	}
	
	public int BuildBalls(ArrayList<Circle> list, String color, int x, int y, int vertical, int horizontal, Rectangle path, int ind) {
		for(int i=0;i<6;i++) {
			Circle c=new Circle(x+(30*i*horizontal),y+(30*i*vertical),10);
			c.setFill(Color.web(color));
			list.add(c);
			PathTransition pathTransition = new PathTransition();  
			pathTransition.setDuration(Duration.millis(5000));
			pathTransition.setCycleCount(Animation.INDEFINITE);
			pathTransition.setInterpolator(Interpolator.LINEAR);
			pathTransition.setPath(path);  
			pathTransition.setNode(c); 
		    pathTransition.setAutoReverse(false);  
		    Transition transition = pathTransition;
            transition.jumpTo(Duration.millis(5000).multiply(ind * 1.0 / 24));
            ind=ind+1;
            transition.play();
 
			this.addGroup(c);
		}
		return ind;
	}
}

class Rhombus extends Obstacle{
	private transient Rectangle purple;
	private transient Rectangle pink;
	private transient Rectangle blue;
	private transient Rectangle yellow;
	private transient ArrayList<Rectangle> shapes;
	
	Rhombus() {
		super();
	}
	
	Rhombus(double X, double Y) {
		super(X,Y);
	}

	@Override
	public void createObstacle(Ball mainBall) {
		  this.initialiseRoot();
		  this.shapes=new ArrayList<Rectangle>();
		  this.pink = new Rectangle();  
		  this.purple = new Rectangle();  
		  this.blue = new Rectangle();  
		  this.yellow = new Rectangle();  
	      this.BuildRect(this.pink, "#FF0181", 110, 75, 200, 30);
	      this.BuildRect(this.purple, "#900DFF", 110, 75, 30 ,200);
	      this.BuildRect(this.yellow, "#FAE100", 110, 245, 200, 30);
	      this.BuildRect(this.blue, "#32DBF0", 280, 75, 30 , 200);
	      this.addGroup(this.pink);
	      this.addGroup(this.purple);
	      this.addGroup(this.blue);
	      this.addGroup(this.yellow);
	      this.shapes.add(this.blue);
	      this.shapes.add(this.yellow);
	      this.shapes.add(this.pink);
	      this.shapes.add(this.purple);
	      this.getObstacle().setLayoutX(this.getX());
		  this.getObstacle().setLayoutY(this.getY());
	}
	
	public ArrayList<Rectangle> getShapes(){
		return this.shapes;
	}
	
	public void BuildRect(Rectangle rectangle, String color, int x, int y, int height, int width) {
		  rectangle.setX(x); 
	      rectangle.setY(y); 
	      rectangle.setWidth(height); 
	      rectangle.setHeight(width); 
	      rectangle.setArcWidth(30.0); 
	      rectangle.setArcHeight(20.0);  
	      rectangle.setFill(Color.web(color));
	}
}


class Cross extends Obstacle{
	private transient Rectangle purple;
	private transient Rectangle pink;
	private transient Rectangle blue;
	private transient Rectangle yellow;
	private transient ArrayList<Rectangle> shapes;
	
	Cross() {
		super();
	}
	
	Cross(double X, double Y) {
		super(X,Y);
	}

	@Override
	public void createObstacle(Ball mainBall) {
		  this.initialiseRoot();
		  this.shapes=new ArrayList<Rectangle>();
		  this.pink = new Rectangle();  
		  this.purple = new Rectangle();  
		  this.blue = new Rectangle();  
		  this.yellow = new Rectangle(); 
	      this.BuildRect(this.pink, "#FF0181", 65, 65, 15, 80);
	      this.BuildRect(this.purple, "#900DFF", 65, 65, 80 ,15);
	      this.BuildRect(this.yellow, "#FAE100", 65, 0, 15, 80);
	      this.BuildRect(this.blue, "#32DBF0", 0, 65, 80 , 15);
	      this.addGroup(this.pink);
	      this.addGroup(this.purple);
	      this.addGroup(this.blue);
	      this.addGroup(this.yellow);
	      this.shapes.add(this.blue);
	      this.shapes.add(this.yellow);
	      this.shapes.add(this.pink);
	      this.shapes.add(this.purple);
	      this.getObstacle().setLayoutX(this.getX());
		  this.getObstacle().setLayoutY(this.getY()); 
	}
	
	public void BuildRect(Rectangle rectangle, String color, int x, int y, int height, int width) {
		  rectangle.setX(x); 
	      rectangle.setY(y); 
	      rectangle.setWidth(height); 
	      rectangle.setHeight(width); 
	      rectangle.setArcWidth(30.0); 
	      rectangle.setArcHeight(20.0);  
	      rectangle.setFill(Color.web(color));
	}
	
	public ArrayList<Rectangle> getShapes(){
		return this.shapes;
	}
}

