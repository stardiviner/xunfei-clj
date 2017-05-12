(ns xunfei-clj.core
  (:import [com.iflytek.cloud.speech
            SpeechRecognizer
            SpeechConstant
            SpeechUtility
            SpeechSynthesizer
            SynthesizerListener]
           [org.json JSONArray JSONObject JSONTokener]
           ))

(def appid (str SpeechConstant/APPID "=59145fb0"))

(def app-init (SpeechUtility/createUtility appid))

;; (.onSpeakProgress (mSynListenerGen) 1 1 1) ;;=> nil
(defn mSynListenerGen
  []
  (proxy [SynthesizerListener] []
    (onCompleted [_])
    (onBufferProgress [^Integer percent  ^Integer beginPos  ^Integer endPos ^String info])
    (onSpeakBegin [])
    (onSpeakPaused [])
    ;; (onSpeakPausedaaaacc [x] x)
    (onSpeakProgress [^Integer percent ^Integer beginPos ^Integer endPos])
    (onSpeakResumed [])
    )
  )

(let [mTts (SpeechSynthesizer/createSynthesizer)
      _ (.setParameter mTts SpeechConstant/VOICE_NAME "xiaoyan")
      _ (.setParameter mTts SpeechConstant/SPEED "50")
      _ (.setParameter mTts SpeechConstant/VOLUME "80")
      _ (.setParameter mTts SpeechConstant/TTS_AUDIO_PATH "./tts_test.pcm")
      ]
  ;; 开始合成
  (.startSpeaking mTts "语音合成测试程序" (mSynListenerGen))
  )

