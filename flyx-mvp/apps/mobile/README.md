# Flyx Mobile App

This directory is a placeholder for the React Native mobile application.

## Integration with Registration Webview

The mobile app will use a WebView component to display the registration page from the `registration-web` project.

### Opening the WebView

Pass the following user data as URL query parameters to pre-fill the form:

- `name`
- `email`
- `phone`
- `userId`
- `token` (JWT for authentication)

Example URL:
`https://app.flyx.com/register/:eventId?name=Aryan&email=aryan@uni.edu&token=jwt_token_here`

### Handling Events from Webview

Listen for `postMessage` events from the WebView to handle successful registrations.

```javascript
// React Native WebView onMessage handler
onMessage={(event) => {
  const data = JSON.parse(event.nativeEvent.data);
  if (data.type === 'REGISTRATION_SUCCESS') {
    // Close the webview and navigate to a success screen
    // e.g., navigation.navigate('TicketConfirmation', { ticketId: data.ticketId });
  }
}}
```
