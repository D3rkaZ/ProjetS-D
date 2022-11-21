package sd.akka.actor;
import akka.actor.ActorSystem;
import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.actor.ActorRef;

public class ActorTP1 extends AbstractActor {
	private ActorRef child;

	private ActorTP1(int n) {
			if(n == 0){
					child = null;
			}
			else {
					child = getContext().actorOf(ActorTP1.props(n-1));
			}
	}

	// Méthode servant à déterminer le comportement de l'acteur lorsqu'il reçoit un message
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(SayHello.class, message -> sayHello(message,getSender()))
				.build();
	}

	private void sayHello(final SayHello message, ActorRef sender) {
		if( child == null)
		{

			sender.tell(new ActorTP1.SayHello(message.name+"END"),getSelf());
			System.out.println(message.name);
		}
		else{
			//Forward
			child.tell(new ActorTP1.SayHello("2"+message.name), ActorRef.noSender());
			System.out.println(message.name);
		}
	}

	// Méthode servant à la création d'un acteur
	public static Props props(int n) {
		return Props.create(ActorTP1.class, n);
	}

	// Définition des messages en inner classes
	public interface Message {}

	public static class SayHello implements Message {
		public final String name;

		public SayHello(String name) {
			this.name = name;
		}
	}
}
