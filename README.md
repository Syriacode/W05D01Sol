# W05D01Sol
I have went through the toturial you posted on Skype. I have found another following parts of the same toturial and worked on them as well!.

what are the new restrictions on Service now in android?
Background Execution Limits:
Apps who run in the background consume some of the limited device resources. That, especially if the user is gaming or watching videos, will affect the User Experience. To maintain that Android 8.0 (API level 26) puts restriction on what apps can do while running in the background.
For example, it a user is playing games, playing music, and browsing the web in the same time may result in poor user experience like shut down the music app suddenly.
Apps are restricted in two ways:
Background Service Limitations: When an app is on idle there are limits to its use of background services and that will not apply for foreground services.
Broadcast Limitations: with very few exceptions, apps will not be able to use their manifest to register for implicit broadcasts. At the runtime they will be able to do that, and they can use the manifest to register for explicit broadcasts targeted specifically at their app.

