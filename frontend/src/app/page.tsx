// app/page.tsx
// This is the ONLY route - represents authenticated content
// Auth logic is handled by AuthGate in layout.tsx

import HomeScreen from "@/modules/app/ui/HomeScreen";

export default function Page() {
  return <HomeScreen />;
}
