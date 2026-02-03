import Navbar from "@/modules/navbar/ui/Navbar";
import styles from "./AppLayoutStyles.module.css";

export default function AppLayout({
    children,
}: {
    children: React.ReactNode;
}) {
    return (
        <div>
            <header className={styles.header}>
                <Navbar></Navbar>
            </header>
            <main className={styles.main}>
                <div className={styles.navbarBack}></div>
                {children}
            </main>
        </div>
    );
}
