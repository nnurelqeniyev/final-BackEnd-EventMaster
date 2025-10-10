document.addEventListener('DOMContentLoaded', function () {
  const yearEl = document.getElementById('year');
  if (yearEl) {
    yearEl.textContent = new Date().getFullYear().toString();
  }

  const form = document.getElementById('subscribe-form');
  if (form) {
    form.addEventListener('submit', function (e) {
      e.preventDefault();
      const input = /** @type {HTMLInputElement|null} */(document.getElementById('email'));
      const email = input ? input.value.trim() : '';
      if (!email) return;
      alert('Abunə üçün təşəkkürlər! ' + email);
      if (input) input.value = '';
    });
  }
});
