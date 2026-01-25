// src/app/page.tsx
"use client";

import { useDispatch, useSelector } from "react-redux";
import type { RootState } from "@/store";
import { increment, decrement } from "@/store/dummySlice";

export default function HomePage() {
  const value = useSelector((state: RootState) => state.dummy.value);
  const dispatch = useDispatch();

  return (
    <main style={{ padding: 24 }}>
      <h1>Redux Test Page</h1>

      <p>Current value: {value}</p>

      <button onClick={() => dispatch(increment())}>+</button>
      <button onClick={() => dispatch(decrement())}>-</button>
    </main>
  );
}
