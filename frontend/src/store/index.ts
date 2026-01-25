/**
 * Uygulamanın global durum takipçisi
 * Bütün durumlar burada kayıtlı olucak ve bu noktadan kontrol edilicekler
 */

import { configureStore } from "@reduxjs/toolkit";
import dummyReducer from "./dummySlice";

export const store = configureStore({
    reducer: {
        dummy: dummyReducer,
    },
    devTools: process.env.NODE_ENV !== "production",
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;