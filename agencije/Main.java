package agencije;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	
	private static ArrayList<Ponuda> ponude = new ArrayList<Ponuda>();
	
	private static Label glavnaLb = new Label("Destinacija");
	private static RadioButton jeftPonude, svePonude;
	private static Button prikaziBtn;
	

	public static void main(String[] args) {
		
		ponude = Ponuda.ucitajPonude();
		
		for (Ponuda ponuda : ponude) {
			if (ponuda.getCena() < 500) {
				System.out.println(ponuda);
			}
		}
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setTitle("Letovanje");
		VBox root = new VBox();
		createGui(root);
		
		Scene scene = new Scene(root, 600, 400);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}
	
	public void createGui(VBox root) {
		
		HBox top = new HBox(); //sadrzace Label i TextField
		HBox middle = new HBox(5); //sadrzaze dva radiodugmeta
		VBox bottom = new VBox(5); // sadrzace Button i TextAread
		
		TextField destTf = new TextField();
		destTf.setMinWidth(200);
		
		top.getChildren().addAll(glavnaLb, destTf);
		top.setPadding(new Insets(30, 0, 0, 15));
		top.setSpacing(15);
		top.setAlignment(Pos.TOP_CENTER);
		top.setMinHeight(130);
		
		jeftPonude = new RadioButton("3 najjeftinije ponude");
		jeftPonude.setSelected(true);
		svePonude = new RadioButton("sve ponude za datum");
		
		jeftPonude.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				glavnaLb.setText("Destinacija");	
			}		
		});
		
		svePonude.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				glavnaLb.setText("Datum");			
			}		
		});
		
		ToggleGroup tg = new ToggleGroup();
		jeftPonude.setToggleGroup(tg);
		svePonude.setToggleGroup(tg);
		
		middle.getChildren().addAll(jeftPonude, svePonude);
		middle.setPadding(new Insets(30));
		middle.setAlignment(Pos.CENTER);
		
		prikaziBtn = new Button("Prikazi");
		TextArea ispisiTa = new TextArea();
		ispisiTa.setEditable(false);
		prikazi(ispisiTa, destTf);
		
		bottom.getChildren().addAll(prikaziBtn, ispisiTa);
		bottom.setAlignment(Pos.BOTTOM_CENTER);
		
		root.getChildren().addAll(top, middle, bottom);
		
	}
	
	public void prikazi(TextArea prikazi, TextField podatak) {
		
		prikaziBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				
				if (jeftPonude.isSelected()) {
					String destinacija = podatak.getText();
					
					if (destinacija.isEmpty())  {
						prikazi.setText("Napisite prvo zeljenu destinaciju.");
					}
					else {
						List<Ponuda> zeljene = new ArrayList<Ponuda>();
						for (Ponuda ponuda : ponude) {
							if (ponuda.getDestinacija().equals(destinacija)) {
								zeljene.add(ponuda);
							}
						}						
						if (zeljene.isEmpty()) {
							prikazi.setText("Nema ponuda za tu destinaciju.");
						}
						else {
							Collections.sort(zeljene);
							zeljene = (zeljene.size() <= 3) ? zeljene : zeljene.subList(0, 3);
							prikazi.setText(Ponuda.listajPonude(zeljene));
						}
					}
				}
				if (svePonude.isSelected()) {
					String datumS = podatak.getText();
					boolean ispravanPodatak = false;
					String text = "";
					Datum zeljeniDatum = null;
					List<Ponuda> zeljene = new ArrayList<Ponuda>();
					
					if (datumS.isEmpty())  {
						prikazi.setText("Napisite prvo zeljeni datum.");
					}
					else {
						try {
							zeljeniDatum = new Datum(datumS);
							ispravanPodatak = true;
						}
						catch (NumberFormatException e) {
							text = "Unesite datum u obliku: dd.mm.gggg.";
						}
						if (ispravanPodatak) {
							for (Ponuda ponuda : ponude) {
/*								if (ponuda.getDatumPolaska().compareTo(zeljeniDatum) <= 0) {
									zeljene.add(ponuda);						
								}*/
								/*
								 * Gornji kod je u slucaju da zelimo ponude koje su pre
								 * unesenog datuma (pisalo u opisu zadatka, ali resenje
								 * koje je dato nije tako radjeno, vec je radjeno kao
								 * po donjem kodu, tj. daju se ponude samo za taj datum).
								 */
								
								if (ponuda.getDatumPolaska().equals(zeljeniDatum)) {
									zeljene.add(ponuda);						
								}	
							}						
							text = (zeljene.isEmpty()) ? "Nema ponuda za taj datum." : Ponuda.listajPonude(zeljene);
						}
						prikazi.setText(text);
					}
				}
			}
							
		});		
	}

}
