## Summary

This server is more sophisticated: it can handle multiple clients at once! When a client connects, it spawns a thread to communicate with the client. A client will send a string to the server and the server will send back the date or time depending on what client has asked for. This repeats until the client sends an `Exit` message.
