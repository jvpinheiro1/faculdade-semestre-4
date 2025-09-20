# Copilot Instructions for CRUD-FETCH (React Native + Expo)

## Project Overview
This is a simple React Native CRUD app scaffolded with Expo. The app is structured for mobile development and uses functional components for user management. The main entry point is `App.js`, with user-related logic in the `componentes/` directory.

## Architecture & Data Flow
- **Main App**: `App.js` sets up the root view and status bar.
- **User Management**: 
  - `componentes/UserForm.js`: Handles user creation via a form. Submits data using `fetch` (currently missing endpoint URL).
  - `componentes/UserList.js`: Intended for listing users (implementation missing or incomplete).
- **Entry Point**: `index.js` registers the app with Expo.
- **Assets**: Images/icons are stored in `assets/`.

## Developer Workflows
- **Start the app**: Use Expo scripts from `package.json`:
  - `npm start` or `expo start` (launch Metro bundler)
  - `npm run android` / `npm run ios` / `npm run web` for platform-specific launches
- **No tests or build scripts** are present; focus is on manual development and debugging via Expo.

## Conventions & Patterns
- **Functional Components**: All components use React hooks (`useState`).
- **User Form**: Calls `onUserAdded` after successful POST to refresh user list.
- **Error Handling**: Errors are logged to console; no user-facing error UI.
- **Styles**: Inline with `StyleSheet.create`.
- **Component Naming**: Files are PascalCase, but exported functions may not match (e.g., `userForm` should be `UserForm`).
- **API Integration**: The POST endpoint in `UserForm.js` is empty; agents should prompt for or configure the backend URL.

## Integration Points
- **External Dependencies**: Uses `expo`, `react`, and `react-native` (see `package.json`).
- **No custom backend or API service abstraction**; direct `fetch` calls are used.

## Key Files & Directories
- `App.js`: Main app UI
- `componentes/UserForm.js`: User creation logic
- `componentes/UserList.js`: User listing (to be implemented)
- `index.js`: App registration
- `assets/`: App icons and images

## Actionable Guidance for AI Agents
- When adding new features, follow the functional component and hook-based pattern.
- Always update the user list after user creation (see `onUserAdded` usage).
- Prompt for missing API endpoints or backend integration details.
- Maintain style and naming conventions as seen in existing files.
- If implementing tests, add a new convention and document it here.

---
_If any section is unclear or incomplete, please provide feedback for further refinement._
