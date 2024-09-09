// src/App.tsx
import Footer from "components/common/Footer";
import Header from "components/common/Header";
import MainRoute from "components/routes/MainRoute";
import { Route, Routes, useLocation } from "react-router-dom";

// firebase setup
import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
import { getMessaging } from "firebase/messaging";
import { get } from "http";
// https://firebase.google.com/docs/web/setup#available-libraries
const firebaseConfig = {
  apiKey: process.env.REACT_APP_FIREBASE_API_KEY,
  authDomain: process.env.REACT_APP_FIREBASE_AUTH_DOMAIN,
  projectId: process.env.REACT_APP_FIREBASE_PROJECT_ID,
  storageBucket: process.env.REACT_APP_FIREBASE_STORAGE_BUCKET,
  messagingSenderId: process.env.REACT_APP_FIREBASE_MESSAGING_SENDER_ID,
  appId: process.env.REACT_APP_FIREBASE_APP_ID,
  measurementId: process.env.REACT_APP_FIREBASE_MEASUREMENT_ID,
};
const firebaseApp = initializeApp(firebaseConfig);
const analytics = getAnalytics(firebaseApp);
const messaging = getMessaging(firebaseApp);

// firebase setup

function App() {
  const location = useLocation();
  const currentPath = location.pathname;

  console.log(currentPath);

  const popupPaths = ["/kapay/approve", "/kapay/cancel", "/kapay/fail"];
  const showHeaderPaths = ["/", "/search"];

  console.log(showHeaderPaths.includes(currentPath));
  const isPopup = window.opener !== null && !window.opener.closed;
  const shouldHideHeaderFooter = popupPaths.includes(currentPath) && isPopup;
  return (
    <div className="pb-[86px] relative">
      {!shouldHideHeaderFooter && showHeaderPaths.includes(currentPath) && <Header />}
      <Routes>
        <Route path="/*" element={<MainRoute />} />
      </Routes>
      {!shouldHideHeaderFooter && <Footer />}
    </div>
  );
}

export default App;
