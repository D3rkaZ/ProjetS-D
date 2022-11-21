package sd.akka;

import java.time.Duration;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import akka.routing.RoundRobinPool;
import sd.akka.actor.ArbitreActor;
import sd.akka.actor.Client;
import sd.akka.actor.ActorTP1;
import sd.akka.actor.JourNuitActor;
import sd.akka.actor.NombrePremierActor;

public class App {
	public static void main(String[] args) {
		ActorSystem actorSystem = ActorSystem.create();
		ActorRef actor = actorSystem.actorOf(Client.props());

		// Envoi de messages simples
		actor.tell(new Client.Retrait(1,200,"Retrait"), ActorRef.noSender());
		actor.tell(new Client.Depot(2,500,"Depot"), ActorRef.noSender());

        actorSystem.terminate();
	}
}
