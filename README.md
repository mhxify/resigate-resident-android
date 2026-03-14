# Secure Resident

Secure Resident is an Android application built with **Kotlin** and **Jetpack Compose**, following **Clean Architecture** principles with a **feature-based package structure**.

The goal of this structure is to keep the codebase clean, scalable, maintainable, and easy to understand as the application grows.

---

## Architecture Overview

This project is organized using:

- **Clean Architecture**
- **Feature-based structure**
- **Separation of concerns**

Instead of grouping the whole project only by technical layers, the code is divided by **features**, and each feature can contain its own:

- `presentation`
- `domain`
- `data`
- `navigation`

This makes each feature more independent and easier to maintain.

---

## Project Structure

```bash
com.secure.resident
│
├── auth
│   ├── data
│   ├── domain
│   ├── navigation
│   └── presentation
│
├── core
│   ├── data
│   ├── di
│   ├── domain
│   ├── navigation
│   └── presentation.component
│
├── landing
│   ├── navigation
│   └── presentation.view
│
├── main
│   ├── data
│   ├── domain
│   ├── navigation
│   └── presentation
│
├── onboarding
│   ├── navigation
│   └── presentation
│
└── ui.theme

```
## Package Description
## auth

This feature contains all logic related to authentication.

It includes:

- `authentication UI screens`

- `business logic`

- `repository contracts and implementations`

- `auth navigation flow`

### Sub-packages:

- **presentation → screens, UI state, composables**

- **domain → use cases, interfaces, business rules**

- **data → repository implementations, models, data sources**

- **navigation → auth-related routes and navigation actions**

## landing

This feature handles the landing / splash flow of the application.

It includes:

- `the landing screen UI`

- `navigation from landing to the next screen`

### Sub-packages:

- **presentation.view**

- **navigation**

## onboarding

This feature contains the onboarding flow shown to the user when entering the app.

It includes:

- `onboarding screens`

- `pager logic`

- `onboarding UI components`

## navigation to the authentication flow

### Sub-packages:

presentation

navigation

## main

This feature contains the main part of the application after the initial flows.

It includes:

- main screens

- main business logic

- related repositories and data logic

- main navigation structure

### Sub-packages:

- presentation

- domain

- data

- navigation

## core

### This package contains shared code used across multiple features.

It centralizes common parts of the application such as:

- reusable UI components

- shared navigation logic

- dependency injection **DI**

- common domain/data utilities

### Sub-packages:

- presentation.component → reusable Compose components

- navigation → app-level navigation setup

- di → dependency injection configuration

- data / domain → shared logic when needed

## ui.theme

### This package contains the global UI theme configuration.

It includes:

- colors

- typography

- dark mode / light mode setup

- Material theme configuration

### Clean Architecture Layers

Each feature can contain the following layers:

`Presentation Layer`

**Responsible for everything related to the UI.**

Examples:

- screens

- composables

- state handling

- user interactions

- view models

`Domain Layer`

**Responsible for the business logic of the feature.**

Examples:

- use cases

- repository contracts

- business rules

`Data Layer`

**Responsible for data handling and implementation details.**

Examples:

- repository implementations

- API/local data source handling

- model mapping

`Navigation Layer`

**Responsible for routing and screen transitions inside the feature.**

## Why This Structure?

**This structure was chosen to achieve the following goals:**

- keep features isolated and easier to manage

- make the code more scalable

- improve readability

- simplify future maintenance

- separate UI, business logic, and data clearly

- make adding new features easier

**It also makes the project easier to explain during code reviews, teamwork, and technical interviews.**

## Development Approach

**The main idea is that each feature should be as self-contained as possible.**

For example, the auth feature contains its own:

- UI

- business logic

- data handling

- navigation

**This reduces coupling between unrelated parts of the app and helps keep the code organized.**

- Reusable Shared Code

- Shared logic that should not belong to one single feature is placed inside the core package.

**This includes things like:**

- reusable text components

- custom buttons

- shared text fields

- app-wide navigation setup

- dependency injection 

- common helpers

**This avoids duplication and keeps feature packages focused only on their own responsibilities.**

## Theme Management

**The project uses a centralized theme system based on MaterialTheme**

`This allows the app to support:`

- consistent colors

- reusable design rules

- light mode

- dark mode

**By using theme values such as:**

- MaterialTheme.colorScheme.primary 

- MaterialTheme.colorScheme.background

- MaterialTheme.colorScheme.surface

- MaterialTheme.colorScheme.onBackground

the UI stays consistent across the whole application.

## Navigation Strategy

**Navigation is separated by feature when necessary, while shared/global navigation logic is placed in the core.navigation package.**

This makes navigation:

- cleaner

- easier to maintain

- less tightly coupled to specific screens

- Scalability

**One of the main advantages of this structure is scalability.**

When adding a new feature, it can follow the same organization:

```bash
feature-name
├── data
├── domain
├── navigation
└── presentation
```
This keeps the project consistent and makes it easier for other developers to understand and contribute.

## Benefits of This Project Structure

- Clear separation of concerns

- Better maintainability

- Easier debugging

- Cleaner collaboration between developers

- Easier feature expansion

- Better readability during interviews and project reviews

## Tech Stack

- Kotlin

- Jetpack Compose

- Material 3

- Navigation Compose

- Clean Architecture

- Feature-based package organization

---

## Developer Information

<p align="center">
  <img src="mhx.png" alt="Mohamed Ali Benouarzeg Logo" width="180"/>
</p>

**Name:** Mohamed Ali Benouarzeg  
**Role:** Android Developer  
**Tech Stack:** Kotlin, Jetpack Compose, Kotlin Multiplatform (KMP)  
**Email:** mohamedbenouarzeg1@gmail.com  
**GitHub:** [mhxify](https://github.com/mhxify)  
**LinkedIn:** [Mohamed Ali Benouarzeg](https://www.linkedin.com/in/mohamed-ali-benouarzeg-3b55582b2/)

---