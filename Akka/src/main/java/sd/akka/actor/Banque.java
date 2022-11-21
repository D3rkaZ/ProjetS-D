package main.java.sd.akka.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import main.java.sd.akka.actor.Banquier;
import akka.actor.ActorRef;

import java.time.Duration;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import akka.pattern.Patterns;


public class Banque extends AbstractActor{
    private ActorRef actor;
    
    public Banque(){
        actor = getContext().actorOf(Banquier.props());
    }

    @Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(Retrait.class, message -> retrait(message))
				.match(Depot.class, message -> depot(message))
				.build();
	}

    private void retrait(final Retrait BanqueActor) {
		System.out.println("Banque :Client N° " + BanqueActor.NClient + ", Somme " + BanqueActor.Somme + ", Méthode : " + BanqueActor.Methode);
        //actor.tell(new Banquier.Retrait(BanqueActor.NClient,BanqueActor.Somme,BanqueActor.Methode), ActorRef.Sender());
        //Demande au banquier 
        CompletionStage<Object> result = Patterns.ask(actor, new Banquier.Retrait(BanqueActor.NClient,BanqueActor.Somme,BanqueActor.Methode), Duration.ofSeconds(10));
        
        System.out.println("Banque : Requêtes récupérée");

        /*
		try {
            //Object s = result.toCompletableFuture().get();
			System.out.println("Banque : Requêtes récupérée");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}*/

        //Réponse au client
        try {
            getSender().tell(result.toCompletableFuture().get(), getSelf());
        } catch (Exception ex) {
            getSender().tell(
            new akka.actor.Status.Failure(ex), getSelf());
            //throw ex;
        }
	}

	private void depot(final Depot BanqueActor) {
		System.out.println("Banque :Client N° " + BanqueActor.NClient + ", Somme " + BanqueActor.Somme + ", Méthode : " + BanqueActor.Methode);
        //actor.tell(new Banquier.Depot(BanqueActor.NClient,BanqueActor.Somme,BanqueActor.Methode), ActorRef.noSender());
        //Demande au banquier 
        CompletionStage<Object> result = Patterns.ask(actor, new Banquier.Depot(BanqueActor.NClient,BanqueActor.Somme,BanqueActor.Methode), Duration.ofSeconds(10));
        
        System.out.println("Banque : Requêtes récupérée");
        
        /*
		try {
            //Object s = result.toCompletableFuture().get();
			System.out.println("Banque : Requêtes récupérée");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}*/

        //Réponse au client
        try {
            getSender().tell(result.toCompletableFuture().get(), getSelf());
        } catch (Exception ex) {
            getSender().tell(
            new akka.actor.Status.Failure(ex), getSelf());
            //throw ex;
        }
	}

    // Méthode servant à la création d'un acteur
	public static Props props() {
		return Props.create(Banque.class);
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
