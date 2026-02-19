// tailwind.config.js
/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {
      fontFamily: {
        sans: ["sans-serif"],
        "montserrat":["Montserrat","sans-serif"],
        "roboto":["Roboto","sans-serif"]
      },
    },
  },
  plugins: [],
};
