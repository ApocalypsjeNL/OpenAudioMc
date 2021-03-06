export class Messages {

    constructor(main) {
        this.fallback = "No message provided in oa+";
        this.main = main;
        this.hueConnected = "You are now connected with your Philips Hue Lights! Please select your group (you can always change this later) and click \"player\" in the left bottem corner to return to the home menu.";
        this.hueLinking = "Press the link button on your hue bridge within %sec% seconds to connect.";
        this.hueWelcome = "We've detected a Philips Hue Bridge! You can connect it to OpenAudioMc and link your in-game session to your lights. This allows your current server to add real-time lighting effects. You can unlink at any time and manually select your lights.";
        this.welcomeMessage = this.fallback;
        this.errorMessage = this.fallback;
    }

    apply() {
        if (document.getElementById("hue-welcome-message") != null) {
            document.getElementById("hue-welcome-message").innerHTML = this.hueWelcome;
        }

        if (this.main.socketModule.state === "ok") {
            document.getElementById("status-message").innerHTML = this.welcomeMessage;
        }
    }

}
