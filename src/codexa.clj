(ns codexa
  (:require [cljfx.api :as fx])
  (:import [javafx.scene.input TransferMode]
           [com.google.zxing.client.j2se MatrixToImageWriter]
           [com.google.zxing.qrcode QRCodeWriter]
           [com.google.zxing.common BitMatrix]
           [com.google.zxing BarcodeFormat]))

(def *state (atom {:text "Drop some text here!"
                   :dragging? false}))
(def ^:const +size+ 64)

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
    ; (MatrixToImageWriter/writeToFile bit-matrix "png" (java.io.File. "qr.png"))
    buffered-image))

(defn root [{:keys [text dragging?]}]
  {:fx/type :stage
   :showing true
   :title "Codexa"
   :width 400
   :height 200
   :scene {:fx/type :scene
           :root {:fx/type :v-box
                  :alignment :center

                  :on-drag-over handle-drag-over
                  :on-drag-dropped handle-drag-dropped
                  :on-drag-entered (fn [_] (swap! *state assoc :dragging? true))
                  :on-drag-exited (fn [_] (swap! *state assoc :dragging? false))

                  :children (if (= text "Drop some text here!")
                              [{:fx/type :label
                                :text text
                                :style {:-fx-font-size 20
                                        :-fx-background-color (if dragging? :lightgreen :transparent)
                                        :-fx-border-radius 12
                                        :fx-padding 20}}
                               {:fx/type :text-field
                                :min-width 128
                                :max-width 128}]

                              ;; If dropped some text then just show these:
                              [{:fx/type :text-field
                                :min-width 128
                                :max-width 128}])}}})

(def renderer
  (fx/create-renderer
   :middleware (fx/wrap-map-desc (fn [state]
                                   (root state)))))

(fx/mount-renderer *state renderer)

