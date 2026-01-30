export default function AppLayout({
    children,
}: {
    children: React.ReactNode;
}) {
    return (
        <div>
            <header>
                <h1>Connex</h1>
                <nav>
                    <span>Home</span>
                    <span>Profile</span>
                </nav>
            </header>
            <main>{children}</main>
        </div>
    );
}
