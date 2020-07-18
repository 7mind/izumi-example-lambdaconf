# Wiring problem definition 

This repository contains a wireframe of a modular application which needs to be *wired*.

There are many different ways to solve the problem, you may want to check out a full [solution](https://github.com/7mind/distage-example) based on [distage](https://izumi.7mind.io/latest/release/doc/distage/)

Feel free to share your thoughts and ideas with us in our Telegram chat: https://t.me/izumi_en  

## Requirements

We need to implement a simple "leaderboard" service with REST-like API, allowing a user to:

- Register their profile for ranking
- Read the content of their profile
- Submit their scores
- Get a ranked list of all the users

The solution must meet the following requirements:

1. The application must support two kinds data storages: PostgreSQL and in-memory(*) one. 
2. The user must be able to select which storage implementations to use with a command-line argument.
3. The application must be functional when in-memory is selected and no PostgreSQL instance available.
4. The application must be able to self-provision its environment(*) when a Docker daemon is running on the local host. 
5. The user must be able to opt-in for self-provisioning.    
6. All the tests of the application's business logic must be execited twice: with both PostgreSQL and in-memory storage implementation.
7. The environment necessary for PostgreSQL tests must be unconditionally provisioned when a Docker daemon is running on the local host. 
   In case the daemon is no available the tests depending on PostgreSQL must be skipped.
8. All the PostgreSQL-depending tests should share just one instance of PostgreSQL docker container. Such a container should be created before all the tests and removed after the test suite exits. 

(*) This is required for integration testing purposes.

## Zoom workshop questions archive

```
From Tushar Mathur to Everyone: (6:24 p.m.)

can you share the discord channel please

From Kai to Everyone: (6:24 p.m.)

https://discord.gg/ymHhUE

From Courtney De Goes to Everyone: (6:26 p.m.)

https://discord.gg/FNenRTN

From Alex Berg to Everyone: (6:27 p.m.)

Thank you for your patience Pavel!

From Gontu Harish to Everyone: (6:28 p.m.)

I happen to hear weird noises from speaker's mic also

From Angeline Tan to Everyone: (6:30 p.m.)

@Parvel what do you mean by dual tests?

From Visar Zejnullahu to Everyone: (6:41 p.m.)

can you please post that youtube link here?

From Kai to Everyone: (6:43 p.m.)

https://www.youtube.com/watch?v=CzpvjkUukAs
https://github.com/7mind/distage-example
https://izumi.7mind.io/distage/
https://twitter.com/shirshovp
https://twitter.com/kai_nyasha
From Mikhail Chugunkov to Everyone: (6:46 p.m.)

+

From Petros Kaklamanis to Everyone: (6:46 p.m.)

yes

From Alex Berg to Everyone: (6:46 p.m.)

yeah

From Vasiliy Efimov to Everyone: (6:46 p.m.)

yes

From Igor Udovichenko to Everyone: (6:46 p.m.)

yes

From Angeline Tan to Everyone: (6:46 p.m.)

tes

From Rafal Pietruch to Everyone: (6:46 p.m.)

y

From Uladzislau Safronau to Everyone: (6:46 p.m.)

Yes

From Visar Zejnullahu to Everyone: (6:46 p.m.)

yep

From Anton Semenov to Everyone: (6:46 p.m.)

Yes.

From Angeline Tan to Everyone: (6:46 p.m.)

yes

From Sundararaman Venkataraman to Everyone: (6:46 p.m.)

yes

From Nikolay Artamonov to Everyone: (6:46 p.m.)

yep

From Vasiliy Efimov to Everyone: (6:47 p.m.)

Fonts are ok

From Alex Berg to Everyone: (6:47 p.m.)

It's ok I think

From Angeline Tan to Everyone: (6:47 p.m.)

Yes that font is ok

From Mikhail Chugunkov to Everyone: (6:47 p.m.)

it's ok

From Sundararaman Venkataraman to Everyone: (6:47 p.m.)

fonts are ok

From Rafal Pietruch to Everyone: (6:47 p.m.)

fonts are fine

From Igor Udovichenko to Everyone: (6:47 p.m.)

will we have access to the record of this presentation?

From Courtney De Goes to Everyone: (6:47 p.m.)

Yes

From Andrey Patseev to Everyone: (6:47 p.m.)

It says I can’t unmute myself

From Courtney De Goes to Everyone: (6:48 p.m.)

:-( Try again?

From Andrey Patseev to Everyone: (6:48 p.m.)

same sadly

From Gastón Schabas to Everyone: (6:49 p.m.)

Can't unmute myself

From Vasiliy Efimov to Everyone: (6:49 p.m.)

same

From Andrey Patseev to Everyone: (6:49 p.m.)

Now works
Thank you

From Angeline Tan to Everyone: (6:50 p.m.)

i cannot unmute

From Gastón Schabas to Everyone: (6:50 p.m.)

not for me. It shows me a message saying that the host doesn't allow me to unmute

From Angeline Tan to Everyone: (6:50 p.m.)

It says “you cannot unmute yourself because the host has muted you”

From Marcos Iriarte to Everyone: (6:50 p.m.)

I could unmute (and right after mute myself again)

From Andrey Patseev to Everyone: (6:50 p.m.)

Message “host asks you to unmute yourself” pops up and I can unmute myself

From Alex Berg to Everyone: (6:51 p.m.)

unmute works for me now

From Valeriy Shinkevich to Everyone: (6:51 p.m.)

Unmute works now

From Kai to Everyone: (6:51 p.m.)

It may work only after “Raise hand”?

From Gastón Schabas to Everyone: (6:51 p.m.)

I recieve a request by the host to unmute me. I accepted the request, then mute me,
and now I can't unmute me again because the host doesn't allow me. I guess the setup
of the meeting is the issue

From Angeline Tan to Everyone: (6:51 p.m.)

ok when the host allows me to unmute, I can do so once, after I mute myself, I cannot
unmute again

From Andrey Patseev to Everyone: (6:52 p.m.)

Courtney it allows you to unmute yourself only once

From dillinger0x04 to Everyone: (6:54 p.m.)

OK I think I'm unmuted

From Gastón Schabas to Everyone: (6:54 p.m.)

I can't find a button to raise my hand. I guess it should be next to the chat button,
share screen and else, right?

From Alex Berg to Everyone: (6:54 p.m.)

It is in the participants view

From Dheeraj Karande to Everyone: (6:54 p.m.)

Go inside participant, at bottom

From dillinger0x04 to Everyone: (6:55 p.m.)

yeah, you have to hover your mouse pointer over the bottom of the screen for it to show up

From Angeline Tan to Everyone: (6:56 p.m.)

@Pavel ok got it. Tks.

From Courtney De Goes to Everyone: (6:56 p.m.)

I just tried to fix the automatic mute setting. Has anything changed?

From Kai to Everyone: (6:56 p.m.)

No

From Angeline Tan to Everyone: (6:56 p.m.)

no

From Courtney De Goes to Everyone: (6:57 p.m.)

:-(

From Angeline Tan to Everyone: (6:57 p.m.)

@Courtney its ok np. We can just raise our hand.

From Kai to Everyone: (6:58 p.m.)

Could be that recording conflicts with voluntary unmute

From Andrey Patseev to Everyone: (6:58 p.m.)

How you raise a hand?

From Kai to Everyone: (6:58 p.m.)

Participants drop-down -> Raise hand
On the bottom-right of the “Participants” dialog

From Andrey Patseev to Everyone: (6:58 p.m.)

Thank you

From Kai to Everyone: (7:05 p.m.)

Hmm, I can unmute now
Can anyone else?

From Andrey Patseev to Everyone: (7:06 p.m.)

Can as weell

From Marcos Iriarte to Everyone: (7:06 p.m.)

yes,

From Semen Nevrev to Everyone: (7:36 p.m.)

so components are wired using plugins?
*by using

From Boris V Kuznetsov to Everyone: (7:37 p.m.)

I guess by Distage DI machinery
@kai ?

From Semen Nevrev to Everyone: (7:40 p.m.)

but I need to mark components on which i depend, and describe them before it^
and only then di stuff will happen. Correct me if I’m wrong

From Kai to Everyone: (7:40 p.m.)

Yes, you specify bindings of interfaces-to-implementations in `ModuleDef`
values and then pass them to distage for wiring. Plugins are a way to expose
bindings indirectly, via classpath scan, instead of passing them as values.
There are some advantages to this, e.g. you only have to create a new Plugin
in a package so that it gets picked up, whereas if you pass them as values
you would also need to modify the point(s) at which you pass them - e.g.
in your main scope and in test scope

From Kai to Everyone: (7:42 p.m.)

Yes, unlike Spring or Guice, distage will not try to instantiate types that
don’t have a specified implementation, and part of that is that it can’t do
anything with such types because it’s not using runtime reflection at all
(distage is also available for Scala.js where there’s no reflection either)

From Kai to Everyone: (7:42 p.m.)

@Semen Nevrev

From Илья Холинов to Everyone: (7:42 p.m.)

are you using kind projector plugin?

From Kai to Everyone: (7:42 p.m.)

Yes

From Boris V Kuznetsov to Everyone: (7:43 p.m.)

You use F[+_,+_]. So you can automatically resolve subclasses and ADT
during module wiring ?

From Kai to Everyone: (7:44 p.m.)

Not sure what do you mean by resolve

From Semen Nevrev to Everyone: (7:44 p.m.)

@kai thanks for the explanation! I guess more info is available in the documentation

From Kai to Everyone: (7:44 p.m.)

Yeah - there’s a doc for plugins - https://izumi.7mind.io/distage/distage-framework.html#plugins

From Angeline Tan to Everyone: (7:45 p.m.)

@Pave, Sorry could you repeat that? The http server? (When you speak quickly,
sometimes your voice breaks up)

From Kai to Everyone: (7:45 p.m.)

http4s Server[IO]

From Boris V Kuznetsov to Everyone: (7:48 p.m.)

Why to tag F[+_,+_] : TagKK ?

From Semen Nevrev to Everyone: (7:49 p.m.)

could you explain what does `weak` and `many` mean?
I guess many means multiple instances of interface could be run
but what about weak?

From Kai to Everyone: (7:51 p.m.)

Tag, TagK, TagKK, etc. contain information about a type required to do the wiring.
This information is available for all known types, but for type variables like `F` 
it must be passed as a type class (from where F was known to be a concrete type),   
so that it gets the information of its actual type

From Kai to Everyone: (7:52 p.m.)

`many[X]` creates a Set binding. That is, you can bind many instances of `X`
here and summon a `Set[X]` of all instances that you have bound. e.g. here
we’ll use this for http routes. We have a Set[HttpApi[F]] and we’re going
to define a few HttpApi’s and expose them

From Kai to Everyone: (7:53 p.m.)

`.weak` creates a _weak_ binding to the set. This works somewhat like weak
collections on the JVM, e.g. WeakReference. It adds an object to the set
only IFF that object is also required by some other binding.

From Kai to Everyone: (7:55 p.m.)

It may be _not_ required for example if you have two main methods (roles)
and only one of them requires that component. So if you run the other main
method, this object will be removed from the Set because it’s not required

From Kai to Everyone: (7:55 p.m.)

https://izumi.7mind.io/distage/advanced-features.html#weak-sets

From Boris V Kuznetsov to Everyone: (7:56 p.m.)

Nice logging. Is that logstage ?

From Kai to Everyone: (7:56 p.m.)

yes

From Boris V Kuznetsov to Everyone: (7:57 p.m.)

So all those `make`, `many`, etc are some macro ?

From Kai to Everyone: (7:59 p.m.)

No, but `.from[X]` uses a macro to generate a constructor for X
And Tag/TagK/etc. are also generated implicitly by macros

From Anton Semenov to Everyone: (7:59 p.m.)

I may be missing something, but should not be the compilation failing
if some bindings are not available?

From Boris V Kuznetsov to Everyone: (8:00 p.m.)

Tag/TagK/etc use a reflection, right? Izumi.reflect package

From Kai to Everyone: (8:01 p.m.)

They use compile-time reflection inside the macro and generate trees
that contain type information

From Semen Nevrev to Everyone: (8:07 p.m.)

So users must duplicate dependencies of modules’ implementations in
ModuleDefs?

From Илья Холинов to Everyone: (8:08 p.m.)

is it possible to define modules depending on other modules? for example
to define api module that explicitly shows that it depends on 2 repositories?

From Kai to Everyone: (8:10 p.m.)

No, modules simply collect bindings, you do not have to define
explicit relationships.
> So users must duplicate dependencies of modules’ implementations in ModuleDefs?

From Angeline Tan to Everyone: (8:11 p.m.)

how many mins is the break

From Kai to Everyone: (8:11 p.m.)

Yeah, you need to add implicits & other parameters

From Semen Nevrev to Everyone: (8:12 p.m.)

u are miuted

From Terry Lin to Everyone: (8:19 p.m.)

does distage replace zio’s zlayer completely or does it coexist
or even complement/enhance it?

From Pavel Shirshov to Everyone: (8:22 p.m.)

Distage can perfectly coexist with zlayer and we found it convenient
to have them both

From Terry Lin to Everyone: (8:24 p.m.)

it’d be great if you can show us an example of the two being
used together :)

From Pavel Shirshov to Everyone: (8:25 p.m.)

zlayer works nice as a service locator for local contexts while
distage works great as a framework wiring together application
components. Even thousands of them.

From Angeline Tan to Everyone: (8:25 p.m.)

yes

From Pavel Shirshov to Everyone: (8:27 p.m.)

So, distage wires your application modules in the beginning of
the application lifecycle and finalises your app, zlayer provides
computation contexts while the application is working. And they
can (and should) perfectly coexist

From Uladzislau Safronau to Everyone: (8:33 p.m.)

yes

From Vasiliy Efimov to Everyone: (8:33 p.m.)

yep

From Semen Nevrev to Everyone: (8:33 p.m.)

better now

From Angeline Tan to Everyone: (8:33 p.m.)

yes

From Semen Nevrev to Everyone: (8:34 p.m.)

maybe zoom has functionaltiy to boost speaker’s audio like in
discord?

From Boris V Kuznetsov to Everyone: (8:34 p.m.)

Can I wire dependencies from another frameworks? I have implicits
and services, implemented in lagom/play

From Boris V Kuznetsov to Everyone: (8:35 p.m.)

The project is forced to use the lagom service locator. Which is
silly, slow and extremely memory consuming Can I use ZLayer or
distage to wire deps from lagom/play? And avoid using lagom service
locator altogether

From Terry Lin to Everyone: (8:36 p.m.)

thanks

From Pavel Shirshov to Everyone: (8:37 p.m.)

Not sure about Lagom, I have no expertise. Most likely you may
do something

From Angeline Tan to Everyone: (8:37 p.m.)

@kai Hi could you push the changes for  zio env to a branch
tks

From Boris V Kuznetsov to Everyone: (8:40 p.m.)

@pshirshov WHat is Kai showing now?

From Angeline Tan to Everyone: (8:40 p.m.)

I think he mentioned docker provision

From Pavel Shirshov to Everyone: (8:41 p.m.)

@Boris: he is writing postgresql boilerplate. That part was
for config deserialization (actually, just your typical pureconfig)

From Boris V Kuznetsov to Everyone: (8:42 p.m.)

So integration with postgres? I mean what was the use case ?

From Angeline Tan to Everyone: (8:43 p.m.)

@Parvel is he working on point 4 in the readme? “The application
must be able to self-provision its environment(*) when a Docker
daemon is running on the local host.”

From Pavel Shirshov to Everyone: (8:44 p.m.)

@Boris He needed to read jdbc driver parameters from config file.
He just wrapped transactor into a convenience helper and right
now he is writing postgresql implementations for Ladder/Profiles

From Boris V Kuznetsov to Everyone: (8:45 p.m.)

thx

From Pavel Shirshov to Everyone: (8:45 p.m.)

@Angeline Tan: he is just implementing PostgreSQL persistence,
so point (1). But he will get to other points very soon

From Angeline Tan to Everyone: (8:46 p.m.)

@Parvel ok tks

From Илья Холинов to Everyone: (8:47 p.m.)

i see sometimes you use '*' and some time '?' . does it makes
sense?

From Pavel Shirshov to Everyone: (8:47 p.m.)

Nope, ? Is a legacy syntax

From Boris V Kuznetsov to Everyone: (8:47 p.m.)

trait FaceDetectService extends Service {
  implicit val requestSerializer = new ByteStringSerializer

  def sendAvatarForCheck: ServiceCall[ByteString, FaceDetectResult]

  override def descriptor: Descriptor = {
    import Service._
    named("face-detect")
      .withCalls(
        restCall(Method.POST, "/detect", sendAvatarForCheck)
      )
      .withAutoAcl(false)
  }
}
@Pavel that's a snippet code with Lagom. Do you think I can use
lagom-based services with Distage ?

From Pavel Shirshov to Everyone: (8:50 p.m.)

@Boris: Probably, it depends on how Lagom instantiates your
components

From Anton Semenov to Everyone: (8:50 p.m.)

What if one wants to use a migration tool, e.g. Flyway, and not
include the DDL in the service definition? Would that mean that
repository modules have to depend on a common migration module
that just ensures that the database is up-to-date?

From Pavel Shirshov to Everyone: (8:52 p.m.)

@Anton Semenov: yup, something like that. You would create another
resource(s) carrying your migrations and add them as dependencies
to your repositories. In case you don’t use a dependency directly,
you may declare it on bindings level and avoid including it in your
constructor

From Anton Semenov to Everyone: (8:54 p.m.)

So it's possible to explicitly define the modules' initialization
order in the second case?

From Pavel Shirshov to Everyone: (8:55 p.m.)

The order is defined by edges of your dependency graph. So yes,
it’s possible to define order, but it wouldn’t be a strict sequential
order. It would be a partial order

From Pavel Shirshov to Everyone: (8:56 p.m.)

For example, there may be a different provisioner instantiating
independent components of your application in parallel

From Anton Semenov to Everyone: (8:56 p.m.)

Ah, sure, a partial order would suffice. Thank you.

From Pavel Shirshov to Everyone: (8:58 p.m.)

Technically that’s what distal does - it turns your bindings
into a DAG, so effectively it defines a partial order for the
steps of your application startup process
*distage
Damn spelling correction :)

From Boris V Kuznetsov to Everyone: (9:01 p.m.)

Do you plan to integrate this with ZIO Test ?

From Pavel Shirshov to Everyone: (9:02 p.m.)

@Boris: maybe. It won't be too hard and we would definitely
welcome a contribution

From Boris V Kuznetsov to Everyone: (9:03 p.m.)

I missed a bit on the start... Which BIO instances are you
using in this piece of code ?

From Pavel Shirshov to Everyone: (9:07 p.m.)

BIO should be enough for most of the purposes, in case you
want to separate capabilities - just explore the BIO hierarchy

From Angeline Tan to Everyone: (9:15 p.m.)

tks

From Kai to Everyone: (9:16 p.m.)

https://izumi.7mind.io/distage/distage-framework-docker.html

From Angeline Tan to Everyone: (9:25 p.m.)

Is the container also removed after the tests have completed?
@kai Nice, tks.

From Boris V Kuznetsov to Everyone: (9:31 p.m.)

So Activation (Repo -> Repo.prod) is a heterogenous map ?
From type to instance

From Pavel Shirshov to Everyone: (9:34 p.m.)

@Angeline Tan: you may choose several policies

From Pavel Shirshov to Everyone: (9:35 p.m.)

In distage 0.10 you may allow/disallow reuse, in case reuse
disallowed the containers will be removed. Since 0.11 it
will be possible to allow reuse between test suites but
drop all the containers once the tests finish

From Pavel Shirshov to Everyone: (9:36 p.m.)

@Boris: nope, it's just a map: case class Activation(activeChoices: Map[Axis, AxisValue])

From Angeline Tan to Everyone: (9:36 p.m.)

@Kai what is the benefit of splitting into 2 roles

From Boris V Kuznetsov to Everyone: (9:37 p.m.)

Guess for testing purposes. Mock and Prod

From Pavel Shirshov to Everyone: (9:39 p.m.)

@Angeline: it's like two separate microservices but able
to run in the same process
It may be very convenient for the following purposes:

From Boris V Kuznetsov to Everyone: (9:40 p.m.)

Does distage support an incremental composition? Like Zlayer >+> ?

From Pavel Shirshov to Everyone: (9:40 p.m.)

1) You may run a "perfect simulation" of your entier
product within the same process
2) It would allow you to increase computational density
of your deployment

From Angeline Tan to Everyone: (9:41 p.m.)

@kai @pavel ok tks

From Kai to Everyone: (9:53 p.m.)

https://izumi.7mind.io/distage/debugging.html#graphviz-rendering

From Kai to Everyone: (9:59 p.m.)

https://github.com/7mind/izumi-example-lambdaconf

From Semen Nevrev to Everyone: (9:59 p.m.)

thanks!
current version is missing PostgresPortCfg
and config package

From Angeline Tan to Everyone: (10:03 p.m.)

I cannot build the latest project locally.

From Pavel Shirshov to Everyone: (10:03 p.m.)

https://github.com/7mind/distage-example

From Angeline Tan to Everyone: (10:03 p.m.)

ok tks

From Semen Nevrev to Everyone: (10:04 p.m.)

okay, thanks

From Angeline Tan to Everyone: (10:07 p.m.)

An example error is get is: 

Error:(4, 22) object docker is not a member of package izumi.distage
import izumi.distage.docker.Docker.{AvailablePort, ClientConfig}

Its ok no problem, I’ll clone the distal-example repo. tks

From Pavel Shirshov to Everyone: (10:12 p.m.)

@Angeline Tan: you may join our chat on telegram, @izumi_en,
we will help you to sort that out

From Angeline Tan to Everyone: (10:13 p.m.)

@Parvel, ok tks,
I'll try the second repo first
No questions so far. Tks for the superb workshop :)

From dillinger0x04 to Everyone: (10:17 p.m.)

can we archive this text thread?

From Kai to Everyone: (10:18 p.m.)

https://izumi.7mind.io/distage/basics.html#multi-dimensionality

From Angeline Tan to Everyone: (10:18 p.m.)

@Parvel the second repo works fine, tks!
From dillinger0x04 to Everyone: (10:20 p.m.)

impressive!

From Semen Nevrev to Everyone: (10:20 p.m.)

thank you for this workshop!

From Sergey Klimov to Everyone: (10:20 p.m.)

thanks!
From Angeline Tan to Everyone: (10:20 p.m.)

seeya

From Semen Nevrev to Everyone: (10:21 p.m.)

bye!

From Terry Lin to Everyone: (10:21 p.m.)

thanks

From Petros Kaklamanis to Everyone: (10:21 p.m.)

thank you !
```