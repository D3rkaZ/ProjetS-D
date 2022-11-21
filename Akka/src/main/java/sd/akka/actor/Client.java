package sd.akka.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.ActorRef;
import main.java.sd.akka.actor.Banque;
import java.time.Duration;
import java.util.concurrent.CompletionStage;
import akka.pattern.Patterns;
import java.util.concurrent.ExecutionException;

public class Client extends AbstractActor {

	private ActorRef actor;

	public Client() {
		actor = getContext().actorOf(Banque.props());
	}

	// Méthode servant à déterminer le comportement de l'acteur lorsqu'il reçoit un message
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(Retrait.class, message -> retrait(message))
				.match(Depot.class, message -> depot(message))
				.build();
	}

	/*private void retrait(final Retrait BanqueActor) {
		System.out.println("Client : Client N° " + BanqueActor.NClient + ", Somme " + BanqueActor.Somme + ", Méthode : " + BanqueActor.Methode);
		actor.tell(new Banque.Retrait(BanqueActor.NClient,BanqueActor.Somme,BanqueActor.Methode), ActorRef.noSender());
	}*/

	private void retrait (final Retrait BanqueActor) {
		System.out.println("Client : Client N° " + BanqueActor.NClient + ", Somme " + BanqueActor.Somme + ", Méthode : " + BanqueActor.Methode);
		CompletionStage<Object> result = Patterns.ask(actor, new Banque.Retrait(BanqueActor.NClient,BanqueActor.Somme,BanqueActor.Methode), Duration.ofSeconds(10));

		try {
			System.out.println("Client :"+result.toCompletableFuture().get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	private void depot(final Depot BanqueActor) {
		System.out.println("Client : Client N° " + BanqueActor.NClient + ", Somme " + BanqueActor.Somme + ", Méthode : " + BanqueActor.Methode);
		//actor.tell(new Banque.Depot(BanqueActor.NClient,BanqueActor.Somme,BanqueActor.Methode), ActorRef.noSender());
		CompletionStage<Object> result = Patterns.ask(actor, new Banque.Depot(BanqueActor.NClient,BanqueActor.Somme,BanqueActor.Methode), Duration.ofSeconds(10));

		try {
			System.out.println("Client :"+result.toCompletableFuture().get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	// Méthode servant à la création d'un acteur
	public static Props props() {
		return Props.create(Client.class);
	}

	// Définition des messages en inner classes
	public interface Message {}

	public static class Depot implements Message {
		public final int NClient;
		public final int Somme;
		public final String Methode;

		public Depot(int NClient,int Somme, String Methode) {
			this.NClient=NClient;
			this.Somme=Somme;
			this.Methode=Methode;
		}
	}

	public static class Retrait implements Message {
		public final int NClient;
		public final int Somme;
		public final String Methode;

		public Retrait(int NClient,int Somme, String Methode) {
			this.NClient=NClient;
			this.Somme=Somme;
			this.Methode=Methode;
		}
	}
}
