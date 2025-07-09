(ns codexa
  (:require [cljfx.api :as fx])
  (:import [javafx.scene.input TransferMode Clipboard KeyCode KeyCodeCombination KeyCombination]
           [javafx.embed.swing SwingFXUtils]
           [com.google.zxing.client.j2se MatrixToImageWriter]
           [com.google.zxing.qrcode QRCodeWriter]
           [com.google.zxing.common BitMatrix]
           [com.google.zxing BarcodeFormat]))

(def ^:const +size+ 128)
(def ^:const +initial-string+ "Drom some text on this window!")
(def *state (atom {:text +initial-string+
                   :dragging? false}))

(defn- handle-drag-over [^javafx.scene.input.DragEvent event]
  (when (.hasString (.getDragboard event))
    (.acceptTransferModes event (into-array TransferMode [TransferMode/COPY]))))

(defn- handle-drag-dropped [^javafx.scene.input.DragEvent event]
  (let [db (.getDragboard event)]
    (when (.hasString db)
      (swap! *state assoc :text (.getString db))
      (.setDropCompleted event true)))
  (.consume event))

(defn- create-qr-image [^String data]
  (let [^QRCodeWriter writer (QRCodeWriter.)
        ^BitMatrix bit-matrix (.encode writer data BarcodeFormat/QR_CODE +size+ +size+)
        buffered-image (MatrixToImageWriter/toBufferedImage bit-matrix)]
    ;; (MatrixToImageWriter/writeToFile bit-matrix "png" (java.io.File. "qr.png"))
    buffered-image))

(defn- handle-paste [_]
  (let [^Clipboard clipboard (Clipboard/getSystemClipboard)]
    (when (.hasString clipboard)
      (swap! *state assoc :text (.getString clipboard)))))

(defn root [{:keys [text dragging?]}]
  {:fx/type :stage
   :showing true
   :title "Codexa"
   :width 400
   :height 400
   :scene {:fx/type :scene
           :accelerators {(new KeyCodeCombination KeyCode/V (into-array javafx.scene.input.KeyCombination$Modifier [KeyCombination/CONTROL_DOWN]))
                          handle-paste}
           :root {:fx/type :v-box
                  :alignment :center

                  :on-drag-over handle-drag-over
                  :on-drag-dropped handle-drag-dropped
                  :on-drag-entered (fn [_] (swap! *state assoc :dragging? true))
                  :on-drag-exited (fn [_] (swap! *state assoc :dragging? false))

                  :children (if (= text +initial-string+)
                              [{:fx/type :label
                                :text text
                                :style {:-fx-font-size 20
                                        :-fx-background-color (if dragging? :lightgreen :transparent)
                                        :-fx-border-radius 12
                                        :fx-padding 20}}
                               {:fx/type :label
                                :text "Alternatively, you can Ctrl + V"}]

                              ;; If dropped some text then just show these:
                              [{:fx/type :image-view
                                :mouse-transparent true
                                :image (SwingFXUtils/toFXImage (create-qr-image text) nil)}
                               {:fx/type :label
                                :text "Alternatively, you can Ctrl + V"}])}}})

(def renderer
  (fx/create-renderer
   :middleware (fx/wrap-map-desc (fn [state]
                                   (root state)))))

(fx/mount-renderer *state renderer)

