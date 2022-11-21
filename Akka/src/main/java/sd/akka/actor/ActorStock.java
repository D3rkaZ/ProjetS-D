/*package sd.akka.actor;
import akka.actor.ActorSystem;
import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.actor.ActorRef;
import java.util.Hashtable;

public class ActorStock extends AbstractActor {

	private Hashtable<Integer, Integer> stock = new Hashtable<Integer, Integer>();

	private ActorStock() {
			stock.put(1, 10);
			stock.put(2, 4);
	}

	// Méthode servant à déterminer le comportement de l'acteur lorsqu'il reçoit un message
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(Objets.class, obj -> gererCommande(obj,getSender()))
				.build();
	}

	private void gererCommande(Objets obj, ActorRef sender) {

			int qte = this.stock.get(obj.identifiantObjet);
			this.stock.put(obj.identifiantObjet, qte + obj.quantite);
			sender.tell("Bonne réception",getSelf());
			System.out.println(this.stock);
	}

	// Méthode servant à la création d'un acteur
	public static Props props() {
		return Props.create(ActorStock.class);
	}

	// Définition des messages en inner classes
	public interface Message {

	public static class SayHello implements Message {
		public final String name;

		public SayHello(String name) {
			this.name = name;
		}

	}


		public static class Objets implements Message {

			public int identifiantObjet;
			public int quantite;

			public Objets(int i, int n){
				this.identifiantObjet = i;
				this.quantite = n;
			}

		}
	}

}*/
