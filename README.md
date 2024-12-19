# Quotify-V2

Quotify-V2 is an advanced version of the Quotify app designed to provide an immersive quote exploration experience. The app leverages a JSON API to fetch inspirational and motivational quotes and integrates AI functionalities to generate personalized quotes based on user queries. However, the AI quote generation feature is currently encountering a `404 error` and is under debugging.

---

## Visual Representation

<img src="https://github.com/user-attachments/assets/5e60e3bb-717b-43ce-9c9a-819275745b87" alt="Screenshot" width="250">
<img src="https://github.com/user-attachments/assets/3ac0d0e1-293c-4339-84c5-479da392c795" alt="Screenshot" width="250">

## Features

- **Fetch Quotes from API**: Retrieve a wide variety of quotes from a JSON API.
- **Interactive Navigation**: Navigate through quotes with 'Previous' and 'Next' buttons.
- **Share Quotes**: Seamlessly share quotes with friends and family via social media or other platforms.
- **AI Integration** *(In Progress)*: Attempted integration of AI to dynamically generate quotes based on user input.

---

## Technologies Used

- **Programming Language**: Kotlin
- **UI Design**: XML
- **Architecture**: MVVM (Model-View-ViewModel)
- **API Endpoint**: JSON API for fetching quotes "https://dummyjson.com/c/4fcb-6aa8-4240-9c6d"
- **AI Integration**: ChatGPT "https://api.openai.com/v1/completions"

---

## Known Issues

- **AI Quote Generation**:
  - The AI integration feature is encountering a `404 error` while attempting to generate quotes. The issue might be related to an incorrect API endpoint.

---

## Future Enhancements

- Debug and resolve the AI quote generation issue.
- Add NLP for recognition of voice commands to AI
- Implement offline caching for quotes.
- Enhance UI for better user engagement.
