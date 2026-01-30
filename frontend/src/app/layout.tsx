import type { Metadata } from "next";
import "@/styles/globals.css";
import StoreProvider from "@/store/provider";
import AuthGate from "@/components/auth/AuthGate";

export const metadata: Metadata = {
  title: "Connex",
  description: "The platform that will guide your career",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body>
        <StoreProvider>
          <AuthGate>{children}</AuthGate>
        </StoreProvider>
      </body>
    </html>
  );
}
