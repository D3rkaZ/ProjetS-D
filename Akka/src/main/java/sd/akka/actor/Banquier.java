package main.java.sd.akka.actor;

import javax.swing.AbstractAction;

import akka.actor.AbstractActor;
import akka.actor.Props;
import main.java.sd.akka.actor.Banque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.time.Duration;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import akka.pattern.Patterns;

public class Banquier extends AbstractActor {
    private Map <Integer,Integer> Compte;
    //private ArrayList <Integer,Map> CompteBanquier; 

    public Banquier()
    {
        Compte = new HashMap();
        //CompteBanquier = new ArrayList<Integer,Map>();

        Compte.put(1, 2000);
        Compte.put(2, 500);

        //CompteBanquier.add(1,Compte);
    }

    @Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(Retrait.class, message -> retrait(message))
				.match(Depot.class, message -> depot(message))
				.build();
	}

    private void retrait(final Retrait BanqueActor) {
        String reponse ;

		System.out.println("Banquier :Client N° " + BanqueActor.NClient + ", Somme " + BanqueActor.Somme + ", Méthode : " + BanqueActor.Methode);
		int SommeInt = Compte.get(BanqueActor.NClient);
        if ( SommeInt-BanqueActor.Somme<0)
        {
            System.out.println("Retrait impossible");
            reponse= "Erreur pas assez d'argent sur le compte, la Somme est de " + BanqueActor.Somme;
            //Réponse a la banque
        try {
            getSender().tell(reponse, getSelf());
        } catch (Exception ex) {
            getSender().tell(
            new akka.actor.Status.Failure(ex), getSelf());
            throw ex;
        }
        }
        else{
            Compte.put(BanqueActor.NClient,(SommeInt-BanqueActor.Somme));
            System.out.println("Montant modifié Retrait");
            reponse = "Nouveau Montant : "+ Compte.get(BanqueActor.NClient);
            try {
                getSender().tell(reponse, getSelf());
            } catch (Exception ex) {
                getSender().tell(
                new akka.actor.Status.Failure(ex), getSelf());
                throw ex;
            }
        }
	}

	private void depot(final Depot BanqueActor) {
        String reponse;

		System.out.println("Banquier :Client N° " + BanqueActor.NClient + ", Somme " + BanqueActor.Somme + ", Méthode : " + BanqueActor.Methode);
        int SommeInt = Compte.get(BanqueActor.NClient);
        Compte.put(BanqueActor.NClient,(SommeInt+BanqueActor.Somme));
        System.out.println("Montant modifié");
            reponse = "Nouveau Montant : "+ Compte.get(BanqueActor.NClient);
            try {
                getSender().tell(reponse, getSelf());
            } catch (Exception ex) {
                getSender().tell(
                new akka.actor.Status.Failure(ex), getSelf());
                throw ex;
            }
}

    // Méthode servant à la création d'un acteur
	public static Props props() {
		return Props.create(Banquier.class);
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
