<p align="center">
  <img src="logo.png" alt="Codexa Logo" width="150"/>
</p>

<h1 align="center">Codexa</h1>

<p align="center">
  A simple, elegant desktop utility to instantly generate QR codes from text.
</p>

<p align="center">
  <a href="LICENSE"><img src="https://img.shields.io/badge/License-MIT-yellow.svg" alt="License: MIT"></a>
  <img src="https://img.shields.io/badge/clojure-1.12.1-blue.svg" alt="Clojure">
  <img src="https://img.shields.io/badge/built%20with-cljfx-brightgreen.svg" alt="Built with cljfx">
  <img src="https://img.shields.io/badge/platform-desktop-lightgrey.svg" alt="Platform: Desktop">
</p>

---

Codexa is a lightweight and intuitive desktop application designed for one purpose: to get a QR code from your text as quickly as possible. With multiple input methods, it integrates seamlessly into your workflow, whether you're a developer needing to test a URL or a user wanting to share a snippet of text.

## Features

Codexa removes all friction between your text and its QR code representation.

*   **Instant QR Code Generation:** The QR code appears the moment you provide the text. No clicks required.
*   **Drag and Drop:** Simply drag any text from a document, website, or editor and drop it onto the Codexa window.
*   **Global Paste:** Just copy your text (`Ctrl+C`) and press `Ctrl+V` while the application is in focus. The QR code will be generated instantly.
*   **Manual Entry:** For direct input, you can always type or paste directly into the provided text field.

## How to Use

1.  Launch the Codexa application.
2.  Provide your text using any of the following methods:
    *   **Drag** a piece of text and **drop** it anywhere on the app window.
    *   **Copy** text to your clipboard and **paste** it (`Ctrl+V`) into the focused app window.
    *   **Click** the text field and **type** or paste your text manually.
3.  Your QR code is immediately displayed, ready to be scanned.

## Technology Stack

Codexa is built with a simple and powerful stack, showcasing the strengths of the Clojure ecosystem for building modern desktop applications.

*   **Language:** [Clojure](https://clojure.org/)
*   **GUI Framework:** [cljfx](https://github.com/cljfx/cljfx) (A declarative, data-driven wrapper around JavaFX)
*   **QR Code Engine:** [ZXing ("Zebra Crossing")](https://github.com/zxing/zxing)


## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
