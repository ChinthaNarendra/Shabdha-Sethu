import { useState, useEffect } from "react";
import "./Home.css";

function Home() {

  const [audioFile, setAudioFile] = useState(null);

  const [inputText, setInputText] = useState("");

  const [outputText, setOutputText] = useState("");

  const [sourceLang, setSourceLang] = useState("auto");

  const [targetLang, setTargetLang] = useState("en");

  const [history, setHistory] = useState([]);

  const [message, setMessage] = useState("");

  const [loading, setLoading] = useState(false);

    const loadHistory = async () => {

    const token = localStorage.getItem("token");

    try {

        const response = await fetch(
            "http://localhost:8080/translate/history",
            {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            }
        );

        if (!response.ok) {

            throw new Error("Unable to load history");

        }

        const data = await response.json();

        const formattedHistory = data.map(item => ({

            input: item.sourceText,

            output: item.translatedText

        }));

        setHistory(formattedHistory);

    }

    catch (error) {

        console.error(error);

    }

};

  useEffect(() => {

    loadHistory();

}, []);

  /* ---------- Speech To Text ---------- */

  const startListening = () => {

    const SpeechRecognition =
      window.SpeechRecognition ||
      window.webkitSpeechRecognition;

    if (!SpeechRecognition) {

      setMessage("Speech Recognition is not supported");

      return;

    }

    const recognition = new SpeechRecognition();

    recognition.lang = "en-US";

    recognition.interimResults = false;

    recognition.maxAlternatives = 1;

    recognition.start();

    recognition.onresult = (event) => {

      const speechText =
        event.results[0][0].transcript;

      setInputText(speechText);

    };

    recognition.onerror = () => {

      setMessage("Speech Recognition Error");

    };

  };



  /* ---------- Translate ---------- */

const translateText = async () => {

    if (!inputText.trim()) {

        setMessage("Please enter text");

        return;

    }

    const token = localStorage.getItem("token");

    setLoading(true);

    try {

        const response = await fetch(
            "http://localhost:8080/translate",
            {
                method: "POST",

                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`
                },

                body: JSON.stringify({
                    text: inputText,
                    sourceLang,
                    targetLang
                })
            }
        );

        if (!response.ok) {

            throw new Error("Translation Failed");

        }

        const translated = await response.text();

        setOutputText(translated);

        await loadHistory();

        setMessage("");

    }

    catch (error) {

        console.error(error);

        setMessage("Translation Failed");

    }

    finally {

        setLoading(false);

    }

};

  /* ---------- Swap Languages ---------- */

  const swapLanguages = () => {

    setSourceLang(targetLang);

    setTargetLang(sourceLang);

    setInputText(outputText);

    setOutputText(inputText);

  };

  /* ---------- Copy ---------- */

  const copyOutput = () => {

    if (!outputText) return;

    navigator.clipboard.writeText(outputText);

    setMessage("Copied Successfully");

    setTimeout(() => {

      setMessage("");

    }, 2000);

  };

  /* ---------- Speak ---------- */

  const speakText = (text) => {

    if (!text) return;

    window.speechSynthesis.cancel();

    const speech = new SpeechSynthesisUtterance(text);
        speech.lang =
      targetLang === "te"
        ? "te-IN"
        : targetLang === "hi"
        ? "hi-IN"
        : "en-US";

    window.speechSynthesis.speak(speech);

  };

  /* ---------- Download ---------- */

  const downloadText = () => {

    if (!outputText) return;

    const element = document.createElement("a");

    const file = new Blob([outputText], {

      type: "text/plain"

    });

    element.href = URL.createObjectURL(file);

    element.download = "translation.txt";

    document.body.appendChild(element);

    element.click();

    document.body.removeChild(element);

  };

  /* ---------- Clear ---------- */

  const clearText = () => {

    setInputText("");

    setOutputText("");

    setMessage("");

  };

  /* ---------- Clear History ---------- */

const clearHistory = async () => {

    const token = localStorage.getItem("token");

    try {

        const response = await fetch(
            "http://localhost:8080/translate/history",
            {
                method: "DELETE",

                headers: {
                    Authorization: `Bearer ${token}`
                }
            }
        );

        if (!response.ok) {

            throw new Error("Unable to clear history");

        }

        setHistory([]);

        setMessage("History Cleared Successfully");

        setTimeout(() => {

            setMessage("");

        }, 2000);

    }

    catch (error) {

        console.error(error);

        setMessage("Failed to clear history");

        setTimeout(() => {

            setMessage("");

        }, 2000);

    }

};

  /* ---------- Audio Upload ---------- */

  const handleAudioUpload = (event) => {

    const file = event.target.files[0];

    if (!file) return;

    setAudioFile(file);

    setMessage("Selected : " + file.name);

    setTimeout(() => {

      setMessage("");

    }, 3000);

  };

  const uploadAudio = async () => {

    if (!audioFile) {

      setMessage("Please select audio");

      return;

    }

    const token = localStorage.getItem("token");

    const formData = new FormData();

    formData.append("file", audioFile);

    try {

      const response = await fetch(

        "http://localhost:8080/api/speech-to-text",

        {

          method: "POST",

          headers: {

            Authorization: `Bearer ${token}`

          },

          body: formData

        }

      );

      const data = await response.json();

      setInputText(data.text);

      setOutputText(data.text);

      setMessage("Audio Converted Successfully");

    }

    catch (error) {

      console.error(error);

      setMessage("Audio Upload Failed");

    }

  };
    return (

    <div className="container">

      <div className="topBar">

        <h1>Shabdha Sethu</h1>

        <button
          className="logoutBtn"
          onClick={() => {

            localStorage.removeItem("token");

            window.location.href = "/";

          }}
        >
          Logout
        </button>

      </div>

      <h3>Speech Translation Web Application</h3>

      <textarea

        value={inputText}

        onChange={(e) => setInputText(e.target.value)}

        placeholder="Type or Speak your text..."

      />

      {

        message && (

          <p className="message">

            {message}

          </p>

        )

      }

      <div className="speechRow">

  <button
    className="speakBtn"
    onClick={startListening}
  >
    🎤 Speak
  </button>

  <label className="fileUpload">

    📁 Select Audio

    <input
      type="file"
      accept="audio/*"
      onChange={handleAudioUpload}
    />

  </label>

  <button
    className="uploadBtn"
    disabled={!audioFile}
    onClick={uploadAudio}
  >
    Upload Audio
  </button>

</div>

      <div className="langRow">

        <div>

          <label>

            Source

          </label>

          <select

            value={sourceLang}

            onChange={(e) =>

              setSourceLang(e.target.value)

            }

          >

            <option value="auto">Auto</option>

            <option value="en">English</option>

            <option value="te">Telugu</option>

            <option value="hi">Hindi</option>

          </select>

        </div>

        <button

          className="swapBtn"

          onClick={swapLanguages}

        >

          ⇄

        </button>
                <div>

          <label>

            Target

          </label>

          <select

            value={targetLang}

            onChange={(e) =>
              setTargetLang(e.target.value)
            }

          >

            <option value="en">English</option>

            <option value="te">Telugu</option>

            <option value="hi">Hindi</option>

          </select>

        </div>

      </div>

      <button
    className="translateBtn"
    onClick={translateText}
    disabled={loading}
>
    {loading ? "⏳ Translating..." : "Translate"}
</button>

      <div className="outputBox">

        <span

          className="copyIcon"

          onClick={copyOutput}

        >

          📋

        </span>

        <p>

          {outputText || "Translated text will appear here..."}

        </p>

      </div>

      <div className="outputActions">

        <button
    className="speakBtn"
    onClick={() => speakText(outputText)}
>
    🔊 Speak
</button>

        <button
    className="downloadBtn"
    onClick={downloadText}
>
    ⬇ Download
</button>

        <button
    className="clearBtn"
    onClick={clearText}
>
    🗑 Clear
</button>

      </div>

      <div className="historyHeader">

        <h3>

          Translation History

        </h3>

        <button

          className="clearHistoryBtn"

          onClick={clearHistory}

        >

          Clear History

        </button>

      </div>
            <ul className="historyList">

        {

          history.length === 0 ? (

            <p className="emptyHistory">

              No Translation History

            </p>

          ) : (

            history.map((item, index) => (

              <li key={index}>

                <strong>

                  {item.input}

                </strong>

                <br />

                ➜ {item.output}

              </li>

            ))

          )

        }

      </ul>

    </div>

  );

}

export default Home;